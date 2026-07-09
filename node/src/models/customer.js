import { getDatabase } from '../config/database.js';
import { DEFAULT_PAGE, DEFAULT_PAGE_SIZE, MAX_PAGE_SIZE } from '../utils/constants.js';

class CustomerService {
  constructor() {
    this.db = getDatabase();
  }

  /**
   * Get all customers with page-based or cursor-based pagination
   */
  getCustomers(page, limit, cursor) {
    return new Promise((resolve, reject) => {
      const pageSize = this.getPageSize(limit);

      if (cursor !== null && cursor !== undefined) {
        // Cursor-based pagination
        this.getCursorPage(cursor, pageSize)
          .then(resolve)
          .catch(reject);
      } else {
        // Page-based pagination
        this.getPagedResult(page, pageSize)
          .then(resolve)
          .catch(reject);
      }
    });
  }

  /**
   * Get paginated results using offset-limit
   */
  getPagedResult(page, pageSize) {
    return new Promise((resolve, reject) => {
      const pageNum = page === null || page === undefined || page < 0 ? DEFAULT_PAGE : page;
      const offset = pageNum * pageSize;

      this.db.get('SELECT COUNT(*) as total FROM customers', (err, row) => {
        if (err) return reject(err);

        const total = row.total;

        this.db.all(
          `SELECT id, first_name as firstName, last_name as lastName, email, phone, address, city, country
           FROM customers
           ORDER BY id ASC
           LIMIT ? OFFSET ?`,
          [pageSize, offset],
          (err, rows) => {
            if (err) return reject(err);

            resolve({
              content: rows || [],
              pageable: {
                pageNumber: pageNum,
                pageSize,
                offset
              },
              totalElements: total,
              totalPages: Math.ceil(total / pageSize),
              first: pageNum === 0,
              last: pageNum * pageSize + (rows?.length || 0) >= total,
              numberOfElements: rows?.length || 0,
              empty: !rows || rows.length === 0
            });
          }
        );
      });
    });
  }

  /**
   * Get cursor-based paginated results
   */
  getCursorPage(cursor, pageSize) {
    return new Promise((resolve, reject) => {
      this.db.all(
        `SELECT id, first_name as firstName, last_name as lastName, email, phone, address, city, country
         FROM customers
         WHERE id > ?
         ORDER BY id ASC
         LIMIT ?`,
        [cursor, pageSize + 1],
        (err, rows) => {
          if (err) return reject(err);

          const customers = rows || [];
          const hasMore = customers.length > pageSize;
          const items = customers.slice(0, pageSize);
          const nextCursor = hasMore && items.length > 0 ? items[items.length - 1].id : null;

          resolve({
            items,
            nextCursor,
            hasMore
          });
        }
      );
    });
  }

  /**
   * Get a single customer by ID
   */
  getCustomerById(custId) {
    return new Promise((resolve, reject) => {
      this.db.get(
        `SELECT id, first_name as firstName, last_name as lastName, email, phone, address, city, country
         FROM customers
         WHERE id = ?`,
        [custId],
        (err, row) => {
          if (err) return reject(err);
          if (!row) {
            const error = new Error(`Customer not found with id: ${custId}`);
            error.statusCode = 404;
            return reject(error);
          }
          resolve(row);
        }
      );
    });
  }

  /**
   * Create a new customer
   */
  createCustomer(customerData) {
    return new Promise((resolve, reject) => {
      // Check for duplicate email
      this.db.get('SELECT id FROM customers WHERE email = ?', [customerData.email], (err, row) => {
        if (err) return reject(err);
        if (row) {
          const error = new Error('Email already exists');
          error.statusCode = 400;
          return reject(error);
        }

        this.db.run(
          `INSERT INTO customers (first_name, last_name, email, phone, address, city, country)
           VALUES (?, ?, ?, ?, ?, ?, ?)`,
          [
            customerData.firstName,
            customerData.lastName,
            customerData.email,
            customerData.phone || null,
            customerData.address,
            customerData.city,
            customerData.country
          ],
          (err) => {
            if (err) return reject(err);

            // Get the last insert rowid from the database
            this.db.get('SELECT last_insert_rowid() as id', (err, row) => {
              if (err) return reject(err);

              const newId = row.id;
              
              // Retrieve the created customer
              this.getCustomerById(newId)
                .then(resolve)
                .catch(reject);
            });
          }
        );
      });
    });
  }

  /**
   * Delete a customer by ID
   */
  deleteCustomer(custId) {
    return new Promise((resolve, reject) => {
      // Verify customer exists
      this.getCustomerById(custId)
        .then(() => {
          this.db.run('DELETE FROM customers WHERE id = ?', [custId], (err) => {
            if (err) return reject(err);
            resolve();
          });
        })
        .catch(reject);
    });
  }

  /**
   * Validate and normalize page size
   */
  getPageSize(limit) {
    if (limit === null || limit === undefined || limit <= 0) {
      return DEFAULT_PAGE_SIZE;
    }
    return Math.min(limit, MAX_PAGE_SIZE);
  }
}

export default CustomerService;
