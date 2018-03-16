package fi.ipscResultServer.repository.repositoryImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fi.ipscResultServer.domain.User;
import fi.ipscResultServer.repository.CustomUserRepository;

public class CustomUserRepositoryImpl implements CustomUserRepository {
	@PersistenceContext
	EntityManager entityManager;
	
	public List<User> findActiveUsers() {
		 String queryString = "SELECT u FROM User u WHERE u.enabled = :enabled";
		 TypedQuery<User> query = entityManager.createQuery(queryString, User.class);
		 return query.setParameter("enabled", true).getResultList();
	}
	
	public User findByUsername(String username) {
		 String queryString = "SELECT u FROM User u WHERE u.username = :username";
		 TypedQuery<User> query = entityManager.createQuery(queryString, User.class);
		 List<User> users = query.setParameter("username", username).getResultList();
		 if (users.size() > 0) return users.get(0);
		 return null;
	}
}
