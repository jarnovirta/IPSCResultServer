package fi.ipscResultServer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.User;
import fi.ipscResultServer.repository.springJDBCRepository.UserRepository;

@Service
public class UserService {
	
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
	
	public List<User> findEnabledUsers() {
		return userRepository.findEnabledUsers();
	}
	
	public User findByUsername(String username) {
		return null;
	}
	
	@Transactional
	public void delete(Long userId) {
		
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
		return true;
	}
}
