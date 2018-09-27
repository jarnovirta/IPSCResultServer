package fi.ipscResultServer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.User;
import fi.ipscResultServer.repository.springJDBCRepository.UserJDBCRepository;

@Service
public class UserService {
	
	@Autowired
	private UserJDBCRepository userRepository;
	
	@Transactional
	public User save(User user) {
		return null;
	}
	
	public User getOne(Long id) {
		return userRepository.getOne(id);
	}
	@Transactional
	public List<User> findEnabledUsers() {
		return null;
	}
	
	public User findByUsername(String username) {
		return null;
	}
	
	@Transactional
	public void delete(Long userId) {
		
	}
	
	@Transactional
	public void setEnabled(Long userId, boolean enabled) {
		
	}
	
	public boolean isCurrentUserAdmin() {
		return true;
	}
}
