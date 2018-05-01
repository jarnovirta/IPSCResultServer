package fi.ipscResultServer.repository.springJPARepository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ipscResultServer.domain.Stage;

public interface SpringJPAStageRepository extends JpaRepository<Stage, Long>{

}
