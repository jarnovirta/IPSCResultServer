package fi.ipscResultServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ipscResultServer.domain.Competitor;

public interface CompetitorRepository extends JpaRepository<Competitor, String> {

}
