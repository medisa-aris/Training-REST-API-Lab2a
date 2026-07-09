/**
 * Centralized error handling middleware
 */
export function errorHandler(err, req, res, next) {
  const statusCode = err.statusCode || 500;
  const message = err.message || 'Internal server error';

  console.error(`[${new Date().toISOString()}] Error:`, err);

  if (statusCode === 404) {
    return res.status(404).json({
      statusCode: 404,
      message
    });
  }

  if (statusCode === 400) {
    return res.status(400).json({
      statusCode: 400,
      message
    });
  }

  res.status(statusCode).json({
    statusCode,
    message
  });
}

/**
 * 404 Not Found middleware
 */
export function notFoundHandler(req, res) {
  res.status(404).json({
    statusCode: 404,
    message: `Route ${req.method} ${req.path} not found`
  });
}
