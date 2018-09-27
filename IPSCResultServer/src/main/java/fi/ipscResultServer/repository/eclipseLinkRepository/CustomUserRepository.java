package fi.ipscResultServer.repository.eclipseLinkRepository;

import java.util.List;

import fi.ipscResultServer.domain.User;

public interface CustomUserRepository {
	public List<User> findActiveUsers();
	public User findByUsername(String username);
}
