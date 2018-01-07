package fi.ipsc_result_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ipsc_result_server.domain.Competitor;

public interface CompetitorRepository extends JpaRepository<Competitor, String> {

}
