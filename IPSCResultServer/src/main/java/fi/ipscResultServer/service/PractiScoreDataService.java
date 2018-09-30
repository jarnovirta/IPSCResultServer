package fi.ipscResultServer.service;

import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.practiScore.PractiScoreMatchData;
import fi.ipscResultServer.exception.DatabaseException;

@Component
public interface PractiScoreDataService {

	public void save(PractiScoreMatchData matchData) throws DatabaseException;

}
