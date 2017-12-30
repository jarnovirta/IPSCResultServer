package fi.ipsc_result_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ipsc_result_server.domain.MatchScore;

public interface MatchScoreRepository extends JpaRepository<MatchScore, Long>{
	
}
