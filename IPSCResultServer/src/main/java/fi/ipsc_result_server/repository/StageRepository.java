package fi.ipsc_result_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ipsc_result_server.domain.Stage;

public interface StageRepository extends JpaRepository<Stage, String>{

}
