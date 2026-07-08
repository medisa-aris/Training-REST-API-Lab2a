package com.training.lab2a.config;

import com.training.lab2a.entity.Customer;
import com.training.lab2a.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final CustomerRepository customerRepository;

    public DataLoader(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) {
        if (customerRepository.count() == 0) {
            customerRepository.saveAll(java.util.List.of(
                    new Customer(null, "Alice", "Johnson", "alice.johnson@example.com", "1234567890", "10 Main Street", "London", "UK"),
                    new Customer(null, "Bob", "Smith", "bob.smith@example.com", "2345678901", "20 Oak Avenue", "Manchester", "UK"),
                    new Customer(null, "Charlie", "Brown", "charlie.brown@example.com", "3456789012", "30 Pine Road", "Liverpool", "UK"),
                    new Customer(null, "Diana", "Prince", "diana.prince@example.com", "4567890123", "40 River Lane", "Bristol", "UK"),
                    new Customer(null, "Ethan", "Miller", "ethan.miller@example.com", "5678901234", "50 Elm Street", "Cardiff", "UK"),
                    new Customer(null, "Fiona", "Davis", "fiona.davis@example.com", "6789012345", "60 Maple Drive", "Edinburgh", "UK"),
                    new Customer(null, "George", "Wilson", "george.wilson@example.com", "7890123456", "70 Cedar Court", "Birmingham", "UK"),
                    new Customer(null, "Hannah", "Moore", "hannah.moore@example.com", "8901234567", "80 Birch Way", "Glasgow", "UK"),
                    new Customer(null, "Ian", "Taylor", "ian.taylor@example.com", "9012345678", "90 Willow Close", "Leeds", "UK"),
                    new Customer(null, "Julia", "Anderson", "julia.anderson@example.com", "0123456789", "100 Ash Grove", "Sheffield", "UK")
            ));
        }
    }
}
