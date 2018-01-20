package fi.ipscResultServer.repository.springJPARepository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ipscResultServer.domain.Match;

public interface SpringJPAMatchRepository extends JpaRepository<Match, String>{

}
