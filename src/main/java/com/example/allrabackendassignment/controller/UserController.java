package com.example.allrabackendassignment.controller;

import com.example.allrabackendassignment.dto.UserRegistrationRequest;
import com.example.allrabackendassignment.entity.User;
import com.example.allrabackendassignment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 새로운 사용자를 생성합니다.
     *
     * @param request 사용자와 고객 정보를 포함하는 요청 객체
     * @return 생성된 사용자 정보
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRegistrationRequest request) {
        User createdUser = userService.addUser(request);
        return ResponseEntity.ok(createdUser);
    }

    /**
     * 주어진 ID로 사용자를 조회합니다.
     *
     * @param id 사용자 ID
     * @return 조회된 사용자 정보
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * 모든 사용자를 조회합니다.
     *
     * @return 모든 사용자 정보 목록
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * 주어진 ID로 사용자를 업데이트합니다.
     *
     * @param id 사용자 ID
     * @param request 사용자와 고객 정보를 포함하는 요청 객체
     * @return 업데이트된 사용자 정보
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserRegistrationRequest request) {
        User updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * 주어진 ID로 사용자를 삭제합니다.
     *
     * @param id 사용자 ID
     * @return 응답 상태
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
