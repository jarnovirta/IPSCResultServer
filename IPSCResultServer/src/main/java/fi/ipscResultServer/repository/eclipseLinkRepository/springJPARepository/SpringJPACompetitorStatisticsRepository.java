package fi.ipscResultServer.repository.eclipseLinkRepository.springJPARepository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ipscResultServer.domain.statistics.CompetitorStatistics;

public interface SpringJPACompetitorStatisticsRepository extends JpaRepository<CompetitorStatistics, Long>{

}
