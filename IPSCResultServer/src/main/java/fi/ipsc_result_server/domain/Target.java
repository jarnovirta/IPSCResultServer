package fi.ipsc_result_server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Target {
	@JsonProperty("target_number")
	int targetNumber;
	
	@JsonProperty("target_reqshots")
	int requiredShots;

	
	public int getRequiredShots() {
		return requiredShots;
	}

	public void setRequiredShots(int requiredShots) {
		this.requiredShots = requiredShots;
	}

	public int getTargetNumber() {
		return targetNumber;
	}

	public void setTargetNumber(int targetNumber) {
		this.targetNumber = targetNumber;
	}
	
}
