package id.com.templates.security.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import id.com.templates.service.UserActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import id.com.templates.model.NamingGrantedAuthority;
import id.com.templates.model.UserDetail;
import id.com.templates.model.auth.User;
import id.com.templates.model.main.Activity;
import id.com.templates.model.menu.Menu;
import id.com.templates.repository.ActivityRepo;
import id.com.templates.repository.MenuRepo;
import id.com.templates.repository.UserRepo;
import id.com.templates.security.AuthService;

@Service
public class AuthServiceImpl implements AuthService{
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private MenuRepo menuRepo;
	@Autowired
	private UserActivityService userActivityService;

	@Override
	public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
		User users = userRepo.findByUserId(user);
		if (users != null) {
			if(!users.isActivated()){
				throw  new BadCredentialsException("Account was not activated");
			}
			if(users.isLocked()){
				throw new BadCredentialsException("User is locked");
			}
			UserDetail details = new UserDetail();
			Date date = new Date();
			details.setUserId(users.getUserId());
			details.setUserName(users.getUserName());
			details.setPassword(users.getPassword());
			details.setActivated(users.isActivated());
			details.setLocked(users.isLocked());
			List<NamingGrantedAuthority> authorities = new ArrayList<NamingGrantedAuthority>();
			authorities.add(new NamingGrantedAuthority(users.getRoleId(), "ROLES"));

			details.setAuthorities(authorities);
			details.setActiveRole(authorities.get(0).getAuthority());
			details.setItems(findMenuItems(authorities.get(0).getAuthority()));
			details.setLoginTime(date);
			return details;

		}else{
			throw new UsernameNotFoundException("Invalid User Name and Password");
		}
	}



	public List<Menu> findMenuItems(String roleId){
		return menuRepo.findMenuByRoleId(roleId);
	}

	@Override
	public UserDetail userDetails() {
		return (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
