package fi.ipscResultServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ipscResultServer.domain.User;

public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {
	
}
