package com.example.allrabackendassignment.service;

import com.example.allrabackendassignment.entity.Customer;
import com.example.allrabackendassignment.entity.User;
import com.example.allrabackendassignment.repository.CustomerRepository;
import com.example.allrabackendassignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 사용자 이름으로 사용자 조회
        User user = userRepository.findByUsername(username);
        if (user == null) {
            // 사용자를 찾을 수 없을 때 예외 발생
            throw new UsernameNotFoundException("User not found");
        }
        // 사용자 정보와 권한 반환
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}
