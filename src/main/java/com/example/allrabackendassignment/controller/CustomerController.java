package com.example.allrabackendassignment.controller;

import com.example.allrabackendassignment.dto.CustomerDTO;
import com.example.allrabackendassignment.entity.Customer;
import com.example.allrabackendassignment.repository.CustomerRepository;
import com.example.allrabackendassignment.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService; // CustomerService 물려 고객을 관리하는 서비스Repository customerRepository;

    /**
     * 새로운 고객을 생성합니다.
     *
     * @param customerDTO 생성할 고객 정보 (DTO)
     * @return 생성된 고객 정보 (DTO)
     */
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO createdCustomer = customerService.addCustomer(customerDTO);
        return ResponseEntity.ok(createdCustomer);
    }

    /**
     * 주어진 ID로 고객을 조회합니다.
     *
     * @param id 고객 ID
     * @return 조회된 고객 정보 (DTO)
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        CustomerDTO customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    /**
     * 모든 고객을 조회합니다.
     *
     * @return 모든 고객 정보 (DTO 목록)
     */
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    /**
     * 주어진 ID로 고객을 업데이트합니다.
     *
     * @param id 고객 ID
     * @param customerDTO 업데이트할 고객 정보 (DTO)
     * @return 업데이트된 고객 정보 (DTO)
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
        return ResponseEntity.ok(updatedCustomer);
    }
}
