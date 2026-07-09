import test from 'node:test';
import assert from 'node:assert/strict';

test('Swagger docs endpoint is available', async () => {
  const response = await fetch('http://127.0.0.1:8080/api-docs/');
  assert.equal(response.status, 200);
});
