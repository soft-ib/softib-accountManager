package com.softib.accountmanager.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.softib.accountmanager.util.JwtUtil;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public UserDetails loadUserByUsername(String jwt) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		if (jwt == null) {
			throw new UsernameNotFoundException("user not found");
		}
		String username = jwtUtil.extractUsername(jwt);
		String role = jwtUtil.extractRole(jwt);
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role));
		return new org.springframework.security.core.userdetails.User(username, "", authorities);
	}

	public static String getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		} else {
			return principal.toString();
		}
	}

}
