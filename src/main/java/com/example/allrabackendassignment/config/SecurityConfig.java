package com.example.allrabackendassignment.config;

import com.example.allrabackendassignment.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 비밀번호 인코더 (BCrypt 사용)
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/register","/ws").permitAll() // 로그인과 회원가입 페이지 허용
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )
                .csrf(AbstractHttpConfigurer::disable)

                .formLogin(form -> form
                        .defaultSuccessUrl("/ws")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // 로그아웃 후 이동할 URL
                );
        return http.build();
    }

}
