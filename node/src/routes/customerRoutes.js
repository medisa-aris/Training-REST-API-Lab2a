import express from 'express';
import CustomerService from '../models/customer.js';
import { validateCustomer } from '../middleware/validation.js';

const router = express.Router();

/**
 * @openapi
 * /v1/customers:
 *   get:
 *     summary: List customers
 *     description: Retrieve customers with optional page-based or cursor-based pagination.
 *     parameters:
 *       - in: query
 *         name: page
 *         schema:
 *           type: integer
 *         description: Page number to retrieve.
 *       - in: query
 *         name: limit
 *         schema:
 *           type: integer
 *         description: Number of customers per page.
 *       - in: query
 *         name: cursor
 *         schema:
 *           type: integer
 *         description: Cursor used for fetching the next page of results.
 *     responses:
 *       200:
 *         description: Customers retrieved successfully.
 *         content:
 *           application/json:
 *             schema:
 *               oneOf:
 *                 - $ref: '#/components/schemas/CustomerPageResponse'
 *                 - $ref: '#/components/schemas/CursorPageResponse'
 *       400:
 *         description: Invalid request parameters.
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/ErrorResponse'
 */
router.get('/customers', async (req, res, next) => {
  try {
    const page = req.query.page ? parseInt(req.query.page, 10) : null;
    const limit = req.query.limit ? parseInt(req.query.limit, 10) : null;
    const cursor = req.query.cursor ? parseInt(req.query.cursor, 10) : null;

    const customerService = new CustomerService();
    const result = await customerService.getCustomers(page, limit, cursor);

    res.status(200).json(result);
  } catch (error) {
    next(error);
  }
});

/**
 * @openapi
 * /v1/customer/{custId}:
 *   get:
 *     summary: Get customer by ID
 *     description: Fetch a single customer by its unique identifier.
 *     parameters:
 *       - in: path
 *         name: custId
 *         required: true
 *         schema:
 *           type: integer
 *         description: Unique customer identifier.
 *     responses:
 *       200:
 *         description: Customer found.
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/Customer'
 *       404:
 *         description: Customer not found.
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/ErrorResponse'
 */
router.get('/customer/:custId', async (req, res, next) => {
  try {
    const custId = parseInt(req.params.custId, 10);
    const customerService = new CustomerService();
    const customer = await customerService.getCustomerById(custId);

    res.status(200).json(customer);
  } catch (error) {
    next(error);
  }
});

/**
 * @openapi
 * /v1/customers:
 *   post:
 *     summary: Create customer
 *     description: Create a new customer from the request body.
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/components/schemas/CustomerCreateRequest'
 *     responses:
 *       201:
 *         description: Customer created successfully.
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/Customer'
 *       400:
 *         description: Invalid customer payload.
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/ErrorResponse'
 */
router.post('/customers', validateCustomer, async (req, res, next) => {
  try {
    const customerService = new CustomerService();
    const newCustomer = await customerService.createCustomer(req.validatedBody);

    res.status(201).json(newCustomer);
  } catch (error) {
    next(error);
  }
});

/**
 * @openapi
 * /v1/customers/{custId}:
 *   delete:
 *     summary: Delete customer
 *     description: Remove a customer by its unique identifier.
 *     parameters:
 *       - in: path
 *         name: custId
 *         required: true
 *         schema:
 *           type: integer
 *         description: Unique customer identifier.
 *     responses:
 *       204:
 *         description: Customer deleted successfully.
 *       404:
 *         description: Customer not found.
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/ErrorResponse'
 */
router.delete('/customers/:custId', async (req, res, next) => {
  try {
    const custId = parseInt(req.params.custId, 10);
    const customerService = new CustomerService();
    await customerService.deleteCustomer(custId);

    res.status(204).send();
  } catch (error) {
    next(error);
  }
});

export default router;
