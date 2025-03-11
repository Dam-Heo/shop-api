package com.example.allrabackendassignment.service;

import com.example.allrabackendassignment.dto.CustomerDTO;
import com.example.allrabackendassignment.entity.Customer;
import com.example.allrabackendassignment.entity.User;
import com.example.allrabackendassignment.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    /**
     * 주어진 ID로 고객을 조회합니다.
     *
     * @param id 고객 ID
     * @return 조회된 고객 정보 (DTO)
     */
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));
        return toDTO(customer);
    }

    /**
     * 모든 고객을 조회합니다.
     *
     * @return 모든 고객 정보 (DTO 목록)
     */
    @Transactional(readOnly = true)
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(this::toDTO).collect(Collectors.toList());
    }

    /**
     * 새로운 고객을 추가합니다.
     *
     * @param customerDTO 추가할 고객 정보 (DTO)
     * @return 추가된 고객 정보 (DTO)
     */
    @Transactional
    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        Customer customer = toEntity(customerDTO);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        Customer savedCustomer = customerRepository.save(customer);
        return toDTO(savedCustomer);
    }

    /**
     * 주어진 ID로 고객을 업데이트합니다.
     *
     * @param id 고객 ID
     * @param customerDTO 업데이트할 고객 정보 (DTO)
     * @return 업데이트된 고객 정보 (DTO)
     */
    @Transactional
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));

        existingCustomer.setAddress(customerDTO.getAddress());
        existingCustomer.setPhoneNumber(customerDTO.getPhoneNumber());
        existingCustomer.setUpdatedAt(LocalDateTime.now());

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return toDTO(updatedCustomer);
    }


    /**
     * 고객 엔티티를 DTO로 변환합니다.
     *
     * @param customer 고객 엔티티
     * @return 변환된 고객 DTO
     */
    private CustomerDTO toDTO(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .userId(customer.getUser().getId())
                .address(customer.getAddress())
                .phoneNumber(customer.getPhoneNumber())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }

    /**
     * 고객 DTO를 엔티티로 변환합니다.
     *
     * @param customerDTO 고객 DTO
     * @return 변환된 고객 엔티티
     */
    private Customer toEntity(CustomerDTO customerDTO) {
        User user = new User();  // User 엔티티는 UserController 등에서 제공받아야 합니다.
        user.setId(customerDTO.getUserId());

        return Customer.builder()
                .id(customerDTO.getId())
                .user(user)
                .address(customerDTO.getAddress())
                .phoneNumber(customerDTO.getPhoneNumber())
                .createdAt(customerDTO.getCreatedAt())
                .updatedAt(customerDTO.getUpdatedAt())
                .build();
    }
}

