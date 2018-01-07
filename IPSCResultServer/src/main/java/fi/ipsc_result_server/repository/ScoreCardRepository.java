package fi.ipsc_result_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ipsc_result_server.domain.ScoreCard;

public interface ScoreCardRepository extends JpaRepository<ScoreCard, Long> {

}
