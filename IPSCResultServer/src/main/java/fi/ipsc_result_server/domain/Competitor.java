package fi.ipsc_result_server.domain;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Competitor implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@JsonProperty("sh_uuid")
	@Column(nullable = false)
	private String uuid;
	
	@JsonProperty("sh_num")
	@Column(nullable = false)
	private int shooterNumber;
	
	@JsonProperty("sh_fn")
	@Column(nullable = false)
	private String firstName;
	
	@JsonProperty("sh_ln")
	@Column(nullable = false)
	private String lastName;
	
	@JsonProperty("sh_dvp")
	@Column(nullable = false)
	private String division;
	
	@JsonProperty("sh_cc")
	private String country;
	
	@JsonProperty("sh_dq")
	@Column(nullable = false)
	private boolean disqualified;
	
	@JsonProperty("sh_grd")
	private char classification;
	
	@JsonProperty("sh_team")
	private String team;
	
	@JsonProperty("sh_mod")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Calendar modifiedDate;
	
	@JsonProperty("sh_pf")
	private String powerFactor;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getShooterNumber() {
		return shooterNumber;
	}

	public void setShooterNumber(int shooterNumber) {
		this.shooterNumber = shooterNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public boolean isDisqualified() {
		return disqualified;
	}

	public void setDisqualified(boolean disqualified) {
		this.disqualified = disqualified;
	}

	public char getClassification() {
		return classification;
	}

	public void setClassification(char classification) {
		this.classification = classification;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public Calendar getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Calendar modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getPowerFactor() {
		return powerFactor;
	}

	public void setPowerFactor(String powerFactor) {
		this.powerFactor = powerFactor;
	}
}
