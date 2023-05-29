package me.dmk.app.configuration;

import lombok.AllArgsConstructor;
import me.dmk.app.handler.LogoutHandler;
import me.dmk.app.handler.auth.AuthFailureHandler;
import me.dmk.app.handler.auth.AuthSuccessHandler;
import me.dmk.app.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Created by DMK on 17.04.2023
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebConfiguration {

    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorization -> authorization
                        .requestMatchers("/panel/**").authenticated()
                        .anyRequest().permitAll()
                ).formLogin(formLogin -> formLogin
                        .usernameParameter("email")
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/panel")
                        .failureHandler(new AuthFailureHandler())
                        .successHandler(new AuthSuccessHandler())
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
        authenticationProvider.setPasswordEncoder(this.userService.passwordEncoder());

        return authenticationProvider;
    }
}
