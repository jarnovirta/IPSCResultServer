package fi.ipsc_result_server.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ipsc_result_server.domain.Match;

public interface MatchRepository extends JpaRepository<Match, String>{

}
