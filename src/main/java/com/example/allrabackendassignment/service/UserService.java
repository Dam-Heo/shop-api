package com.example.allrabackendassignment.service;

import com.example.allrabackendassignment.dto.CustomerDTO;
import com.example.allrabackendassignment.dto.UserRegistrationRequest;
import com.example.allrabackendassignment.entity.User;
import com.example.allrabackendassignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;

    /**
     * 주어진 ID로 사용자를 조회합니다.
     *
     * @param id 사용자 ID
     * @return 조회된 사용자 정보
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    /**
     * 모든 사용자를 조회합니다.
     *
     * @return 모든 사용자 정보 목록
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * 새로운 사용자를 추가합니다.
     *
     * @param request 사용자와 고객 정보를 포함하는 요청 객체
     * @return 추가된 사용자 정보
     */
    @Transactional
    public User addUser(UserRegistrationRequest request) {
        User user = request.getUser();
        user.setPassword(passwordEncoder.encode(request.getUser().getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);

        // 새로운 사용자를 위한 Customer 생성
        CustomerDTO customerDTO = request.getCustomer();
        customerDTO.setUserId(savedUser.getId());
        customerService.addCustomer(customerDTO);

        return savedUser;
    }

    /**
     * 주어진 ID로 사용자를 업데이트합니다.
     *
     * @param id 사용자 ID
     * @param request 사용자와 고객 정보를 포함하는 요청 객체
     * @return 업데이트된 사용자 정보
     */
    @Transactional
    public User updateUser(Long id, UserRegistrationRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        User user = request.getUser();
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());
        existingUser.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(existingUser);

        // 고객 정보 업데이트
        CustomerDTO customerDTO = request.getCustomer();
        customerService.updateCustomer(id, customerDTO);

        return updatedUser;
    }

    /**
     * 주어진 ID로 사용자를 삭제합니다.
     *
     * @param id 사용자 ID
     */
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
