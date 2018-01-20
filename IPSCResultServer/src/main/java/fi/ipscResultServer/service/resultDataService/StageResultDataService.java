package fi.ipscResultServer.service.resultDataService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.IPSCDivision;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.domain.ResultData.StageResultData;
import fi.ipscResultServer.domain.ResultData.StageResultDataLine;
import fi.ipscResultServer.repository.StageResultDataRepository;
import fi.ipscResultServer.service.ScoreCardService;

@Service
public class StageResultDataService {
	
	@Autowired
	StageResultDataRepository stageResultDataRepository;
	
	@Autowired
	ScoreCardService scoreCardService;
	
	public StageResultData findByStageAndDivision(String stageId, IPSCDivision division) {
		return stageResultDataRepository.findByStageAndDivision(stageId, division);
	}

	public List<StageResultData> findByStage(Stage stage) {
		return stageResultDataRepository.findByStage(stage);
	}
	public List<StageResultDataLine> findStageResultDataLinesByCompetitor(Competitor competitor) {
		return stageResultDataRepository.findStageResultDataLinesByCompetitor(competitor);
	}
	@Transactional
	public void generateStageResultsListing(Stage stage) {
		List<IPSCDivision> divisions = new ArrayList<IPSCDivision>();
		divisions.addAll(stage.getMatch().getDivisions());
		divisions.add(IPSCDivision.COMBINED);
		int divisionsWithResults = 0;
		for (IPSCDivision division : divisions) {
			StageResultData stageResultData = new StageResultData(stage, division);
			
			List<StageResultDataLine> dataLines = new ArrayList<StageResultDataLine>();
			List<ScoreCard> scoreCards;
			// Only generate data for combined divisions if at least 2 divisions have results. 
			if (division == IPSCDivision.COMBINED) {
				if (divisionsWithResults < 2) {
					continue;
				}
				else {
					scoreCards = scoreCardService.findByStage(stage.getId());
				}
			}
			else scoreCards = scoreCardService.findByStageAndDivision(stage.getId(), division);
			Collections.sort(scoreCards);
			
			double topHitFactor = -1.0;
			double topPoints = -1.0;
			int rank = 1;
			for (ScoreCard scoreCard : scoreCards) {
				// Discard results for DQ'ed competitor
				if (scoreCard.getCompetitor().isDisqualified()) continue;
				if (rank == 1) topHitFactor = scoreCard.getHitFactor();
				StageResultDataLine resultDataLine = new StageResultDataLine();
				resultDataLine.setStageRank(rank);
				resultDataLine.setStageResultData(stageResultData);
				resultDataLine.setScoreCard(scoreCard);
				resultDataLine.setCompetitor(scoreCard.getCompetitor());
				resultDataLine.setStagePoints((scoreCard.getHitFactor() / topHitFactor) * stage.getMaxPoints()); 
				scoreCard.setStageRank(rank);
				scoreCard = scoreCardService.save(scoreCard);
				if (rank == 1) topPoints = resultDataLine.getStagePoints();
				resultDataLine.setStageScorePercentage(resultDataLine.getStagePoints() / topPoints * 100);
				dataLines.add(resultDataLine);
				
				if (!stage.getMatch().getDivisionsWithResults().contains(scoreCard.getCompetitor().getDivision())) {
					stage.getMatch().getDivisionsWithResults().add(scoreCard.getCompetitor().getDivision());
				}
				rank++;
			}
			
			if (dataLines.size() > 0) { 
				divisionsWithResults++;
				stageResultData.setDataLines(dataLines);
				stageResultDataRepository.save(stageResultData);
			}
		}
	}
	
	@Transactional
	public void deleteByStage(Stage stage) {
		List<StageResultData> oldStageResultData = findByStage(stage);
		if (oldStageResultData != null) {
			for (StageResultData data : oldStageResultData) {
				stageResultDataRepository.delete(data);
			}
		}
	}
	@Transactional
	public void deleteByMatch(Match match) {
		for (Stage stage : match.getStages()) {
			List<StageResultData> data = findByStage(stage);
			for (StageResultData d : data) {
				stageResultDataRepository.delete(d);
			}
		}
	}
}
