package fi.ipsc_result_server.domain;

public enum IPSCDivision {
	OPEN, PRODUCTION, STANDARD, CLASSIC, REVOLVER, COMBINED;
	
	@Override
	public String toString() {
		return super.toString().substring(0, 1).toUpperCase() 
				+ super.toString().substring(1).toLowerCase();
	}
}
