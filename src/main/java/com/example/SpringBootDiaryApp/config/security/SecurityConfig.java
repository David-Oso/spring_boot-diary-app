package com.example.SpringBootDiaryApp.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    private final String[] AUTHENTICATION_WHITE_LIST = {"/api/v1/user/**"};

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
//        http
//                .csrf().disable().authorizeHttpRequests()
//                .requestMatchers(
//                        "/api/v1/user/**",
//                        "/swagger-resources",
//                        "/swagger-resources/**",
//                        "/swagger-ui/**",
//                        "/swagger-ui.html"
//                ).permitAll()
//                .requestMatchers("/api/v1/user/**").hasAnyRole(USER.name())
//                .requestMatchers(GET, "/api/v1/user/**").hasAnyAuthority(USER.name())
//                .requestMatchers(POST, "/api/v1/user/**").hasAnyAuthority(USER.name())
//                .requestMatchers(DELETE, "/api/v1/user/**").hasAnyAuthority(USER.name())
//                .requestMatchers(PUT, "/api/v1/user/**").hasAnyAuthority(USER.name())
//                .anyRequest()
//                .authenticated()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .logout()
//                .logoutUrl("/api/v1/user/logout")
//                .addLogoutHandler(logoutHandler)
//                .logoutSuccessHandler((request, response, authentication)-> SecurityContextHolder.clearContext());
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http.csrf()
                .disable().cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterAt(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, AUTHENTICATION_WHITE_LIST)
                .permitAll()
                .and()
                .authorizeHttpRequests().anyRequest()
                .authenticated()
                .and()
                .logout()
                .logoutUrl("/api/v1/user/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication)-> SecurityContextHolder.clearContext());
        return http.build();
    }
}
