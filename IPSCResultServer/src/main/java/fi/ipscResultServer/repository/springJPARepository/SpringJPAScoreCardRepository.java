package fi.ipscResultServer.repository.springJPARepository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ipscResultServer.domain.ScoreCard;

public interface SpringJPAScoreCardRepository extends JpaRepository<ScoreCard, Long> {

}
