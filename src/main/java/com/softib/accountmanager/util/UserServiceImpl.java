package com.softib.accountmanager.util;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;



@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public String getCurrentUserRole() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String role = "";
		if (principal instanceof UserDetails) {
			role	=((UserDetails)principal).getAuthorities().iterator().next().getAuthority();
		} else {
			role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority();
		}
		return role;
	}
	

	@Override
	public String getCurrentUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = null;
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		return username;
	}
	
	
}
