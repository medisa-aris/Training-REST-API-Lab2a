package com.training.lab2a.services;

import com.training.lab2a.dto.CursorPageResponse;
import com.training.lab2a.entity.Customer;
import com.training.lab2a.exception.ResourceNotFoundException;
import com.training.lab2a.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repo;

    public Object getCustomers(Integer page, Integer limit, Long cursor) {
        if (cursor != null) {
            int pageSize = (limit == null || limit <= 0) ? 20 : Math.min(limit, 100);
            Page<Customer> customerPage = repo.findByIdGreaterThanOrderByIdAsc(cursor, PageRequest.of(0, pageSize + 1));
            List<Customer> customers = customerPage.getContent();
            boolean hasMore = customers.size() > pageSize;
            List<Customer> pageItems = customers.stream().limit(pageSize).toList();
            Long nextCursor = hasMore && !pageItems.isEmpty() ? pageItems.get(pageItems.size() - 1).getId() : null;
            return new CursorPageResponse<>(pageItems, nextCursor, hasMore);
        }

        int pageNumber = (page == null || page < 0) ? 0 : page;
        int pageSize = (limit == null || limit <= 0) ? 20 : Math.min(limit, 100);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return repo.findAll(pageable);
    }

    public Customer getCustomerById(Long custId) {
        return repo.findById(custId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + custId));
    }

    public Customer createCustomer(Customer customer) {
        return repo.save(customer);
    }

    public void deleteCustomer(Long custId) {
        Customer customer = getCustomerById(custId);
        repo.delete(customer);
    }
}
