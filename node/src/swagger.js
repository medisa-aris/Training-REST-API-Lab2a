import swaggerJsdoc from 'swagger-jsdoc';
import swaggerUi from 'swagger-ui-express';

const swaggerOptions = {
  definition: {
    openapi: '3.0.0',
    info: {
      title: 'ACME Travel API',
      version: '1.0.0',
      description: 'Node.js implementation of the customer management REST API with Swagger documentation.'
    },
    servers: [
      {
        url: 'http://localhost:8080',
        description: 'Local development server'
      }
    ],
    components: {
      schemas: {
        Customer: {
          type: 'object',
          required: ['firstName', 'lastName', 'email', 'address', 'city', 'country'],
          properties: {
            id: { type: 'integer', example: 1 },
            firstName: { type: 'string', example: 'John' },
            lastName: { type: 'string', example: 'Doe' },
            email: { type: 'string', example: 'john.doe@example.com' },
            phone: { type: 'string', nullable: true, example: '+1234567890' },
            address: { type: 'string', example: '123 Main St' },
            city: { type: 'string', example: 'New York' },
            country: { type: 'string', example: 'USA' }
          }
        },
        CustomerCreateRequest: {
          type: 'object',
          required: ['firstName', 'lastName', 'email', 'address', 'city', 'country'],
          properties: {
            firstName: { type: 'string', example: 'John' },
            lastName: { type: 'string', example: 'Doe' },
            email: { type: 'string', example: 'john.doe@example.com' },
            phone: { type: 'string', nullable: true, example: '+1234567890' },
            address: { type: 'string', example: '123 Main St' },
            city: { type: 'string', example: 'New York' },
            country: { type: 'string', example: 'USA' }
          }
        },
        CustomerPageResponse: {
          type: 'object',
          properties: {
            content: { type: 'array', items: { $ref: '#/components/schemas/Customer' } },
            pageable: {
              type: 'object',
              properties: {
                pageNumber: { type: 'integer', example: 0 },
                pageSize: { type: 'integer', example: 10 },
                offset: { type: 'integer', example: 0 }
              }
            },
            totalElements: { type: 'integer', example: 1 },
            totalPages: { type: 'integer', example: 1 },
            first: { type: 'boolean', example: true },
            last: { type: 'boolean', example: true },
            numberOfElements: { type: 'integer', example: 1 },
            empty: { type: 'boolean', example: false }
          }
        },
        CursorPageResponse: {
          type: 'object',
          properties: {
            items: { type: 'array', items: { $ref: '#/components/schemas/Customer' } },
            nextCursor: { type: 'integer', nullable: true, example: 10 },
            hasMore: { type: 'boolean', example: true }
          }
        },
        ErrorResponse: {
          type: 'object',
          properties: {
            statusCode: { type: 'integer', example: 404 },
            message: { type: 'string', example: 'Customer not found' }
          }
        }
      }
    }
  },
  apis: ['./src/routes/*.js']
};

const swaggerSpec = swaggerJsdoc(swaggerOptions);

export function setupSwagger(app) {
  app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerSpec));
  app.get('/api-docs.json', (req, res) => {
    res.setHeader('Content-Type', 'application/json');
    res.send(swaggerSpec);
  });
}
