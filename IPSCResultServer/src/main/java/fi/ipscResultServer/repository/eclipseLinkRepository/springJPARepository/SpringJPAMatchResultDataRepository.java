package fi.ipscResultServer.repository.eclipseLinkRepository.springJPARepository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ipscResultServer.domain.resultData.MatchResultData;

public interface SpringJPAMatchResultDataRepository extends JpaRepository<MatchResultData, Long>{

}
