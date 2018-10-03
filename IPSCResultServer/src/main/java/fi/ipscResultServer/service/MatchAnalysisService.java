package fi.ipscResultServer.service;

import org.springframework.stereotype.Component;

import fi.ipscResultServer.controller.matchAnalysis.MatchAnalysis;

@Component
public interface MatchAnalysisService {

	public MatchAnalysis getMatchAnalysisData(String matchPractiScoreId, 
			String competitorPractiScoreId, String compareToCompetitorPractiScoreId);
}
