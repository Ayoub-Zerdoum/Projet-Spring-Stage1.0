package com.springers.SECURITY;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig  /*extends WebSecurityConfigurerAdapter*/{

	private final JwtAuthentification jwtAuthentification;
	

	private final AuthenticationProvider ap;
	
	public SecurityConfig(JwtAuthentification jwtAuthentification,AuthenticationProvider ap) {
		this.jwtAuthentification = jwtAuthentification;
		this.ap = ap;
	}
	/*
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		System.out.println("checking filter");
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(ap)
                .addFilterBefore(jwtAuthentification, UsernamePasswordAuthenticationFilter.class);
		
        

        return http.build();
    }*/
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		System.out.println("checking filter");
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(ap)
                .addFilterBefore(jwtAuthentification, UsernamePasswordAuthenticationFilter.class);
		
        

        return http.build();
    }
}
