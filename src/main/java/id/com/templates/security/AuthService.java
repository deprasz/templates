package id.com.templates.security;

import org.springframework.security.core.userdetails.UserDetailsService;

import id.com.templates.model.UserDetail;

public interface AuthService extends UserDetailsService{
	UserDetail userDetails();
}
