package fi.ipscResultServer.controller.data;

public class LiveResultServiceConfig {
	private int resultTableRows = 15;
	private int pageChangeInterval = 10;
	private String matchPractiScoreId;
	
	public int getResultTableRows() {
		return resultTableRows;
	}
	public void setResultTableRows(int resultTableRows) {
		this.resultTableRows = resultTableRows;
	}
	public int getPageChangeInterval() {
		return pageChangeInterval;
	}
	public void setPageChangeInterval(int pageChangeInterval) {
		this.pageChangeInterval = pageChangeInterval;
	}
	public String getMatchPractiScoreId() {
		return matchPractiScoreId;
	}
	public void setMatchPractiScoreId(String matchPractiScoreId) {
		this.matchPractiScoreId = matchPractiScoreId;
	}
}
