package com.springers.SECURITY;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springers.UTILITIS.JwtService;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthentification extends OncePerRequestFilter {
	
	
	private final UserDetailsService userDetailsService;
	
	private final JwtService jwtService;
	
	public JwtAuthentification(UserDetailsService userDetailsService,JwtService jwtService) {
		this.userDetailsService=userDetailsService;
		this.jwtService = jwtService;
	}
	
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain)
			throws ServletException, IOException {
	final String authead= request.getHeader("Autorisation");
	final String Jwt;
	final String user_name;
	
		if(!authead.startsWith("Bearer") || authead == null  ) {
			 filterChain.doFilter(request, response);
			 return;
		}
		
		
		Jwt=authead.substring(7);
		user_name = jwtService.extractUsername(Jwt);
		if(user_name!=null|SecurityContextHolder.getContext().getAuthentication() == null) {
			// extrat userdetails 
			UserDetails userDetails= userDetailsService.loadUserByUsername(user_name);
			if (jwtService.validateToken(Jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
		}
		
	}


