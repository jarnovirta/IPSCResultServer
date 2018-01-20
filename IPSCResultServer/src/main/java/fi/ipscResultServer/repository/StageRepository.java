package fi.ipscResultServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ipscResultServer.domain.Stage;

public interface StageRepository extends JpaRepository<Stage, String>{

}
