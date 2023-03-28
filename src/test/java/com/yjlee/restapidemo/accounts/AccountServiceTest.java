package com.yjlee.restapidemo.accounts;

import com.yjlee.restapidemo.common.AppProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AppProperties appProperties;

    @Test
    void findByUsername() {
        UserDetailsService userDetailsService = (UserDetailsService) accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(appProperties.getUserUsername());

        assertTrue(passwordEncoder.matches(appProperties.getUserPassword(), userDetails.getPassword()));
    }

    @Test
    void findByUsernameFail() {
        String username = "random@email.com";
        assertThrows(UsernameNotFoundException.class, () -> accountService.loadUserByUsername(username));

        try {
            accountService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            assertTrue(e.getMessage().contains(username));
        }
    }
}