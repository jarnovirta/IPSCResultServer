package fi.ipscResultServer.repository.springJPARepository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ipscResultServer.domain.ResultData.MatchResultData;

public interface SpringJPAMatchResultDataRepository extends JpaRepository<MatchResultData, String>{

}
