package fi.ipsc_result_server.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@UuidGenerator(name="COMPETITOR_ID_GEN")
public class Competitor implements Serializable, Comparable<Competitor> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@JsonProperty("sh_uuid")
	@GeneratedValue(generator="COMPETITOR_ID_GEN")
	@Column(columnDefinition = "BINARY(128)")
	private UUID uuid;
	
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

	@Override
	public int compareTo(Competitor compareToCompetitor) {
	    final int EQUAL = 0;
	    
	    if (this == compareToCompetitor) return EQUAL;
	    int lastNameComparison = this.lastName.compareTo(compareToCompetitor.getLastName());
	    if (lastNameComparison != EQUAL) return lastNameComparison;
	    return this.firstName.compareTo(compareToCompetitor.getFirstName());
	}
	

	public UUID getUuid() {
		return uuid;
	}


	public void setUuid(UUID uuid) {
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
