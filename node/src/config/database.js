import sqlite3 from 'sqlite3';

let db = null;

export function initializeDatabase() {
  // Create in-memory database matching H2 in-memory behavior
  db = new sqlite3.Database(':memory:', (err) => {
    if (err) {
      console.error('Database connection error:', err);
      throw err;
    }
  });
  
  // Enable foreign keys
  db.run('PRAGMA foreign_keys = ON');
  
  // Create customers table
  db.exec(`
    CREATE TABLE IF NOT EXISTS customers (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      first_name TEXT NOT NULL,
      last_name TEXT NOT NULL,
      email TEXT NOT NULL UNIQUE,
      phone TEXT,
      address TEXT NOT NULL,
      city TEXT NOT NULL,
      country TEXT NOT NULL,
      created_at DATETIME DEFAULT CURRENT_TIMESTAMP
    )
  `, (err) => {
    if (err) {
      console.error('Database setup error:', err);
      throw err;
    }
  });
  
  console.log('Database initialized successfully (in-memory)');
  return db;
}

export function getDatabase() {
  if (!db) {
    throw new Error('Database not initialized. Call initializeDatabase() first.');
  }
  return db;
}

export function closeDatabase() {
  if (db) {
    db.close((err) => {
      if (err) {
        console.error('Error closing database:', err);
      }
    });
    db = null;
  }
}
