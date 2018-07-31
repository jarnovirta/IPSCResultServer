package fi.ipscResultServer.repository.repositoryImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import fi.ipscResultServer.domain.User;
import fi.ipscResultServer.repository.CustomUserRepository;

public class CustomUserRepositoryImpl implements CustomUserRepository {
	@PersistenceContext
	EntityManager entityManager;
	
	final static Logger logger = Logger.getLogger(CustomUserRepositoryImpl.class);
		
	public List<User> findActiveUsers() {
		try {
			 String queryString = "SELECT u FROM User u WHERE u.enabled = :enabled";
			 TypedQuery<User> query = entityManager.createQuery(queryString, User.class);
			 return query.setParameter("enabled", true).getResultList();
		}
		catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	public User findByUsername(String username) {
		try {
			String queryString = "SELECT u FROM User u WHERE u.username = :username";
			TypedQuery<User> query = entityManager.createQuery(queryString, User.class);
			List<User> users = query.setParameter("username", username).getResultList();
			if (users.size() > 0) return users.get(0);
			return null;
		}
		catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
}
