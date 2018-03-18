package fi.ipscResultServer.repository.springJPARepository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ipscResultServer.domain.resultData.StageResultData;

public interface SpringJPAStageResultDataRepository extends JpaRepository<StageResultData, Long>{

}
