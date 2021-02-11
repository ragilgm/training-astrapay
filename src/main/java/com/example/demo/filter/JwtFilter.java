package com.example.demo.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.configuration.MyUserDetailServices;
import com.example.demo.util.JWTUtil;


@Component
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	JWTUtil jwtUtil;
	
	@Autowired
	MyUserDetailServices userDetailServices;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String autorizationHeader = request.getHeader("Authorization");
		String username = null;
		String jwt = null;
		
		if(autorizationHeader!=null && autorizationHeader.startsWith("Bearer ")) {
			jwt = autorizationHeader.substring(7);
			username= jwtUtil.extractUsername(jwt);
		}
		
		System.out.println("context holder :"+SecurityContextHolder.getContext().getAuthentication());
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails = this.userDetailServices.loadUserByUsername(username);
			
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails,null, userDetails.getAuthorities());
			
			System.out.println("authenticationToken : "+authenticationToken);
			
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		
		System.out.println("filterChain :" +filterChain);
		filterChain.doFilter(request, response);
		
		
	}

}
