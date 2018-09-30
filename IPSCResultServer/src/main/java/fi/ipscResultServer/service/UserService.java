package fi.ipscResultServer.service;

import java.util.List;

import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.User;

@Component
public interface UserService {
	
	public User saveOrUpdate(User user);
	
	public User getOne(Long id);
	
	public User findByUsername(String username);
	
	public List<User> findEnabledUsers();
	
	public void setEnabled(Long userId, boolean enabled);
	
	public boolean isCurrentUserAdmin();
	
}
