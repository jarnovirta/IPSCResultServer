package fi.ipscResultServer.repository.springJDBCRepository;

import java.util.List;

import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.User;

@Component
public interface UserRepository {
	public User getOne(Long id);
	
	public User save(User user);
	
	public void updateUser(User user);
				
	public List<User> findEnabledUsers();
		
	public User findByUsername(String username);
	
}
