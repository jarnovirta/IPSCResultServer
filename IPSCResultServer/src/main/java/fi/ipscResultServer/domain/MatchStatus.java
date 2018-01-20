package fi.ipscResultServer.domain;

public enum MatchStatus {
	CLOSED, SCORING, SCORING_ENDED;
	
	@Override
	public String toString() {
		return super.toString().substring(0, 1).toUpperCase() 
				+ super.toString().substring(1).toLowerCase().replace("_", " ");
	}
}
