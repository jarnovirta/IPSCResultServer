package fi.ipscResultServer.repository.eclipseLinkRepository.springJPARepository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ipscResultServer.domain.Competitor;

public interface SpringJPACompetitorRepository extends JpaRepository<Competitor, Long> {

}
