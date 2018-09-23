package fi.ipscResultServer.repository.springJPARepository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ipscResultServer.domain.resultData.MatchResultDataLine;

public interface SpringJPAMatchResultDataLineRepository extends JpaRepository<MatchResultDataLine, Long> {

}

