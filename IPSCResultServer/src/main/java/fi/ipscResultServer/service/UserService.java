package fi.ipscResultServer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.User;
import fi.ipscResultServer.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public User save(User user) {
		return userRepository.save(user);
	}
	
	public User getOne(Long id) {
		return userRepository.getOne(id);
	}
	@Transactional
	public List<User> findEnabledUsers() {
		return userRepository.findActiveUsers();
	}
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Transactional
	public void delete(Long userId) {
		userRepository.delete(userRepository.getOne(userId));
	}
	
	@Transactional
	public void setEnabled(Long userId, boolean enabled) {
		User user = userRepository.getOne(userId);
		user.setEnabled(enabled);
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
