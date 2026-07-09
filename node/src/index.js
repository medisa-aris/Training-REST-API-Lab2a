import express from 'express';
import cors from 'cors';

import { initializeDatabase, closeDatabase } from './config/database.js';
import customerRoutes from './routes/customerRoutes.js';
import { errorHandler, notFoundHandler } from './middleware/errorHandler.js';
import { setupSwagger } from './swagger.js';

const app = express();
const PORT = 8080;

// Initialize database
initializeDatabase();

// Middleware
app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Swagger docs
setupSwagger(app);

// Routes
app.use('/v1', customerRoutes);

// 404 handler
app.use(notFoundHandler);

// Error handler (must be last)
app.use(errorHandler);

// Start server
const server = app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});

// Graceful shutdown
process.on('SIGINT', () => {
  console.log('\nShutting down gracefully...');
  server.close(() => {
    closeDatabase();
    console.log('Server and database closed.');
    process.exit(0);
  });
});

export default app;
