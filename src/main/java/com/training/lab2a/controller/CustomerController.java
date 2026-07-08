package com.training.lab2a.controller;

import com.training.lab2a.dto.CursorPageResponse;
import com.training.lab2a.entity.Customer;
import com.training.lab2a.exception.ResourceNotFoundException;
import com.training.lab2a.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import java.util.List;

@RestController
@RequestMapping("/v1")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers")
    public Object getCustomers(@RequestParam(required = false) Integer page,
                               @RequestParam(required = false) Integer limit,
                               @RequestParam(required = false) Long cursor) {
        if (cursor != null) {
            int pageSize = (limit == null || limit <= 0) ? 20 : Math.min(limit, 100);
            Page<Customer> customerPage = customerRepository.findByIdGreaterThanOrderByIdAsc(cursor, PageRequest.of(0, pageSize + 1));
            List<Customer> customers = customerPage.getContent();
            boolean hasMore = customers.size() > pageSize;
            List<Customer> pageItems = customers.stream().limit(pageSize).toList();
            Long nextCursor = hasMore && !pageItems.isEmpty() ? pageItems.get(pageItems.size() - 1).getId() : null;
            return new CursorPageResponse<>(pageItems, nextCursor, hasMore);
        }

        int pageNumber = (page == null || page < 0) ? 0 : page;
        int pageSize = (limit == null || limit <= 0) ? 20 : Math.min(limit, 100);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return customerRepository.findAll(pageable);
    }

    @GetMapping("/customer/{custId}")
    public Customer getCustomer(@PathVariable Long custId) {
        return customerRepository.findById(custId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + custId));
    }

    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @DeleteMapping("/customers/{custId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Long custId) {
        Customer customer = customerRepository.findById(custId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + custId));
        customerRepository.delete(customer);
    }
}
