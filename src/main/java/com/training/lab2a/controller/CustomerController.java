package com.training.lab2a.controller;

import com.training.lab2a.entity.Customer;
import com.training.lab2a.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/v1")
@SecurityRequirement(name = "bearerAuth")   // Task 5 wires this to Swagger
@Tag(name = "Customers", description = "Endpoints for managing customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    @Operation(summary = "List customers", description = "Retrieve customers with optional pagination or cursor-based paging")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customers retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    @PreAuthorize("hasAnyRole('AGENT','MANAGER','ADMIN')")
    public Object getCustomers(
            @Parameter(description = "Page number to retrieve", example = "0") @RequestParam(required = false) Integer page,
            @Parameter(description = "Number of customers per page", example = "10") @RequestParam(required = false) Integer limit,
            @Parameter(description = "Cursor used for fetching the next page of results", example = "123") @RequestParam(required = false) Long cursor) {
        return customerService.getCustomers(page, limit, cursor);
    }

    @GetMapping("/customer/{custId}")
    @Operation(summary = "Get customer by ID", description = "Fetch a single customer by its unique identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer found"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @PreAuthorize("hasAnyRole('AGENT','MANAGER','ADMIN')")
    public Customer getCustomer(
            @Parameter(description = "Unique customer identifier", required = true, example = "1") @PathVariable Long custId) {
        return customerService.getCustomerById(custId);
    }

    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create customer", description = "Create a new customer from the request body")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Customer created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid customer payload")
    })
    @PreAuthorize("hasAnyRole('AGENT','ADMIN')")
    public Customer createCustomer(
            @Parameter(description = "Customer details to create", required = true) @Valid @RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @DeleteMapping("/customers/{custId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete customer", description = "Remove a customer by its unique identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Customer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @PreAuthorize("hasRole('ADMIN')")            // 403 for anyone else
    public void deleteCustomer(
            @Parameter(description = "Unique customer identifier", required = true, example = "1") @PathVariable Long custId) {
        customerService.deleteCustomer(custId);
    }
}
