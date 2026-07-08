package com.training.lab2a.controller;

import com.training.lab2a.entity.Customer;
import com.training.lab2a.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public Object getCustomers(@RequestParam(required = false) Integer page,
                               @RequestParam(required = false) Integer limit,
                               @RequestParam(required = false) Long cursor) {
        return customerService.getCustomers(page, limit, cursor);
    }

    @GetMapping("/customer/{custId}")
    public Customer getCustomer(@PathVariable Long custId) {
        return customerService.getCustomerById(custId);
    }

    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @DeleteMapping("/customers/{custId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Long custId) {
        customerService.deleteCustomer(custId);
    }
}
