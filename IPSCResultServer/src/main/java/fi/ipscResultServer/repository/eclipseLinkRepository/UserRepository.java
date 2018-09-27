package fi.ipscResultServer.repository.eclipseLinkRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ipscResultServer.domain.User;

public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {
	
}
