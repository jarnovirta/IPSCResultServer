package fi.ipscResultServer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.User;
import fi.ipscResultServer.repository.springJDBCRepository.UserRepository;
import fi.ipscResultServer.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public User saveOrUpdate(User user) {
		if (user.getId() != null) userRepository.updateUser(user);
		else user = userRepository.save(user);
		return user;
	}
	
	public User getOne(Long id) {
		return userRepository.getOne(id);
	}
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public List<User> findEnabledUsers() {
		return userRepository.findEnabledUsers();
	}
	
	@Transactional
	public void setEnabled(Long userId, boolean enabled) {
		User user = userRepository.getOne(userId);
		if (user != null) {
			user.setEnabled(false);
			saveOrUpdate(user);
		}
	}
	
	public boolean isCurrentUserAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean admin = false;
		for (GrantedAuthority auth : authentication.getAuthorities()) {
			if (auth.getAuthority().equals("ROLE_ADMIN")) admin = true; 
		}
		return admin;
	}
}
