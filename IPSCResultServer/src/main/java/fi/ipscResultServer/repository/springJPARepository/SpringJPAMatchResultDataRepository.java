package fi.ipscResultServer.repository.springJPARepository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ipscResultServer.domain.resultData.MatchResultData;

public interface SpringJPAMatchResultDataRepository extends JpaRepository<MatchResultData, Long>{

}
