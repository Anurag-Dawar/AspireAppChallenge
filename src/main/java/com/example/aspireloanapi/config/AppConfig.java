package com.example.aspireloanapi.config;

import com.example.aspireloanapi.enums.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class AppConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.builder().
                username("ADMIN@Aspire")
                .password(passwordEncoder().encode("ADMIN@123")).roles(UserRole.ADMIN.name()).
                build();

        UserDetails customer = User.builder().
                username("CUSTOMER1@Aspire")
                .password(passwordEncoder().encode("CUSTOMER@1")).roles(UserRole.CUSTOMER.name()).
                build();

        UserDetails customer2 = User.builder().
                username("CUSTOMER2@Aspire")
                .password(passwordEncoder().encode("CUSTOMER@2")).roles(UserRole.CUSTOMER.name()).
                build();

        return new InMemoryUserDetailsManager(userDetails, customer, customer2);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}
