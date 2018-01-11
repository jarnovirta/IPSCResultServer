package fi.ipsc_result_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ipsc_result_server.domain.ResultData.StageResultData;

public interface StageResultDataRepository extends JpaRepository<StageResultData, Long>{

}
