package me.dmk.app.configuration;

import me.dmk.app.handler.FailureHandler;
import me.dmk.app.handler.LogoutHandler;
import me.dmk.app.handler.SuccessHandler;
import me.dmk.app.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Created by DMK on 17.04.2023
 */

@Configuration
@EnableWebSecurity
public class WebConfiguration {

    @Autowired
    private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeHttpRequests(authorization -> authorization
                        .requestMatchers("/panel/**").authenticated()
                        .anyRequest().permitAll()
                ).formLogin(formLogin -> formLogin
                        .usernameParameter("email")
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/panel")
                        .failureHandler(new FailureHandler())
                        .successHandler(new SuccessHandler())
                        .permitAll()
                ).logout(logout -> logout
                        .logoutUrl("/panel/logout")
                        .logoutSuccessHandler(new LogoutHandler())
                );

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(this.userService);
        authenticationProvider.setPasswordEncoder(this.passwordEncoder());

        return authenticationProvider;
    }
}
