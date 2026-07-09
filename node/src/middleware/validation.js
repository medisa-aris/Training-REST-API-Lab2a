import Joi from 'joi';
import { VALIDATION_CONSTRAINTS } from '../utils/constants.js';

const customerSchema = Joi.object({
  firstName: Joi.string()
    .required()
    .min(VALIDATION_CONSTRAINTS.firstName.minLength)
    .max(VALIDATION_CONSTRAINTS.firstName.maxLength)
    .messages({
      'string.empty': 'First name is required',
      'string.max': `First name must not exceed ${VALIDATION_CONSTRAINTS.firstName.maxLength} characters`
    }),
  lastName: Joi.string()
    .required()
    .min(VALIDATION_CONSTRAINTS.lastName.minLength)
    .max(VALIDATION_CONSTRAINTS.lastName.maxLength)
    .messages({
      'string.empty': 'Last name is required',
      'string.max': `Last name must not exceed ${VALIDATION_CONSTRAINTS.lastName.maxLength} characters`
    }),
  email: Joi.string()
    .required()
    .email()
    .max(VALIDATION_CONSTRAINTS.email.maxLength)
    .messages({
      'string.empty': 'Email is required',
      'string.email': 'Email should be a valid email address',
      'string.max': `Email must not exceed ${VALIDATION_CONSTRAINTS.email.maxLength} characters`
    }),
  phone: Joi.string()
    .allow(null, '')
    .regex(/^[0-9+\(\)\-\s]{7,15}$/)
    .max(VALIDATION_CONSTRAINTS.phone.maxLength)
    .messages({
      'string.pattern.base': 'Phone number should be valid',
      'string.max': `Phone must not exceed ${VALIDATION_CONSTRAINTS.phone.maxLength} characters`
    }),
  address: Joi.string()
    .required()
    .min(VALIDATION_CONSTRAINTS.address.minLength)
    .max(VALIDATION_CONSTRAINTS.address.maxLength)
    .messages({
      'string.empty': 'Address is required',
      'string.max': `Address must not exceed ${VALIDATION_CONSTRAINTS.address.maxLength} characters`
    }),
  city: Joi.string()
    .required()
    .min(VALIDATION_CONSTRAINTS.city.minLength)
    .max(VALIDATION_CONSTRAINTS.city.maxLength)
    .messages({
      'string.empty': 'City is required',
      'string.max': `City must not exceed ${VALIDATION_CONSTRAINTS.city.maxLength} characters`
    }),
  country: Joi.string()
    .required()
    .min(VALIDATION_CONSTRAINTS.country.minLength)
    .max(VALIDATION_CONSTRAINTS.country.maxLength)
    .messages({
      'string.empty': 'Country is required',
      'string.max': `Country must not exceed ${VALIDATION_CONSTRAINTS.country.maxLength} characters`
    })
}).unknown(false);

/**
 * Validate customer request body
 */
export function validateCustomer(req, res, next) {
  const { error, value } = customerSchema.validate(req.body);

  if (error) {
    const validationErrors = error.details.map(detail => ({
      field: detail.path.join('.'),
      message: detail.message
    }));
    return res.status(400).json({
      statusCode: 400,
      message: 'Validation failed',
      errors: validationErrors
    });
  }

  req.validatedBody = value;
  next();
}
