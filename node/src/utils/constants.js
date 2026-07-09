// Validation constants matching Java entity constraints
export const VALIDATION_CONSTRAINTS = {
  firstName: {
    minLength: 1,
    maxLength: 50,
    required: true
  },
  lastName: {
    minLength: 1,
    maxLength: 50,
    required: true
  },
  email: {
    maxLength: 100,
    required: true,
    pattern: /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  },
  phone: {
    maxLength: 20,
    pattern: /^[0-9+\(\)\-\s]{7,15}$/,
    required: false
  },
  address: {
    minLength: 1,
    maxLength: 200,
    required: true
  },
  city: {
    minLength: 1,
    maxLength: 100,
    required: true
  },
  country: {
    minLength: 1,
    maxLength: 100,
    required: true
  }
};

export const DEFAULT_PAGE_SIZE = 20;
export const MAX_PAGE_SIZE = 100;
export const DEFAULT_PAGE = 0;
