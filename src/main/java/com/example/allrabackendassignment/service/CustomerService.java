package com.example.allrabackendassignment.service;

import com.example.allrabackendassignment.dto.CustomerDTO;
import com.example.allrabackendassignment.entity.Customer;
import com.example.allrabackendassignment.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));
        return toDTO(customer);
    }

    @Transactional
    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        Customer customer = toEntity(customerDTO);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        Customer savedCustomer = customerRepository.save(customer);
        return toDTO(savedCustomer);
    }

    @Transactional
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));

        existingCustomer.setName(customerDTO.getName());
        existingCustomer.setUpdatedAt(LocalDateTime.now());

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return toDTO(updatedCustomer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    private CustomerDTO toDTO(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }

    private Customer toEntity(CustomerDTO customerDTO) {
        return Customer.builder()
                .id(customerDTO.getId())
                .name(customerDTO.getName())
                .createdAt(customerDTO.getCreatedAt())
                .updatedAt(customerDTO.getUpdatedAt())
                .build();
    }
}

