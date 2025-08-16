package com.tanveer.authservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
  private final AuthenticationProvider authenticationProvider;
  private final LogoutHandler logoutHandler;
  private final JwtAuthorizationFilter jwtAuthorizationFilter;

  @Autowired
  public SecurityConfig(AuthenticationProvider authenticationProvider,
                        LogoutHandler logoutHandler, JwtAuthorizationFilter jwtAuthorizationFilter) {
    this.authenticationProvider = authenticationProvider;
    this.logoutHandler = logoutHandler;
    this.jwtAuthorizationFilter = jwtAuthorizationFilter;
  }


  private static final String[] WHITE_LIST_URL = {
    "/swagger-ui/**", "/swagger-ui.html",
    "/swagger-resources/**", "/swagger-resources",
    "/configuration-ui", "/configuration/security",
    "/webjars/**", "/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**",
    "/api/v1/auth/signup", "/api/v1/auth/login"};


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.csrf(AbstractHttpConfigurer::disable)
      .exceptionHandling((exception) -> exception.authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
      .authorizeHttpRequests(auth -> auth
        .requestMatchers(
          WHITE_LIST_URL).permitAll()
        .anyRequest().authenticated()
      )
      .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authenticationProvider(authenticationProvider)
      .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
      .logout((logout) -> logout
        .addLogoutHandler(logoutHandler).logoutUrl("/api/v1/auth/logout")
        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()));
    return http.build();
  }
}
