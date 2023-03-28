package com.yjlee.restapidemo.config;

import com.yjlee.restapidemo.accounts.Account;
import com.yjlee.restapidemo.accounts.AccountRole;
import com.yjlee.restapidemo.accounts.AccountService;
import com.yjlee.restapidemo.common.AppProperties;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {

            @Autowired
            AccountService accountService;

            @Autowired
            AppProperties appProperties;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                createUser(appProperties.getAdminUsername(), appProperties.getAdminPassword());
                createUser(appProperties.getUserUsername(), appProperties.getUserPassword());

            }

            private void createUser(String username, String password) {
                try {
                    accountService.loadUserByUsername(username);
                } catch (UsernameNotFoundException e) {
                    Account account = Account.builder()
                            .email(username)
                            .password(password)
                            .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                            .build();
                    accountService.saveAccount(account);
                }
            }
        };
    }

}
