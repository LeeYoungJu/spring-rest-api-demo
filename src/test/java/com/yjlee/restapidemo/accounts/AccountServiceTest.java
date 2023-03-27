package com.yjlee.restapidemo.accounts;

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

    @Test
    void findByUsername() {
        String username = "yjlee@gmail.com";
        String password = "pass";

        Account account = Account.builder()
                .email(username)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();
        accountService.saveAccount(account);

        UserDetailsService userDetailsService = (UserDetailsService) accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertTrue(passwordEncoder.matches(password, userDetails.getPassword()));
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