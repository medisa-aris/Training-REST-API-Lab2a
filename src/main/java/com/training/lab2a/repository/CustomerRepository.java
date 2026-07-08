package com.training.lab2a.repository;

import com.training.lab2a.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Page<Customer> findAll(Pageable pageable);

    Page<Customer> findByIdGreaterThanOrderByIdAsc(Long cursor, Pageable pageable);
}
