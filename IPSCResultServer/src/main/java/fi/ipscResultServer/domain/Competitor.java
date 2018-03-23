package fi.ipscResultServer.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Entity
public class Competitor implements Serializable, Comparable<Competitor> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@JsonProperty("sh_uid")
	@Column(length = 36)
	private String id;
	
	@JsonProperty("sh_num")
	@Column(nullable = false)
	private int shooterNumber;
	
	@JsonProperty("sh_fn")
	@Column(nullable = false)
	private String firstName;
	
	@JsonProperty("sh_ln")
	@Column(nullable = false)
	private String lastName;
	
	@JsonProperty("sh_id")
	private int ipscAlias;
	
	@JsonProperty("sh_dvp")
	@Column(nullable = false)
	private String division;
	
	@ElementCollection
	private List<String> categories;
	
	@JsonProperty("sh_ctgs")
	@Transient
	private String practiScoreCategoryString;
		
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
	
	@JsonProperty("sh_del")
	private boolean deleted = false; 
	
	@JsonProperty("sh_pf")
	@Transient
	private String practiScorePowerFactorString;
	
	private PowerFactor powerFactor;
	
	@JsonProperty("sh_sqd")
	private int squad;

	@Override
	public int compareTo(Competitor compareToCompetitor) {
	    final int EQUAL = 0;
	    
	    if (this == compareToCompetitor) return EQUAL;
	    int lastNameComparison = this.lastName.compareTo(compareToCompetitor.getLastName());
	    if (lastNameComparison != EQUAL) return lastNameComparison;
	    return this.firstName.compareTo(compareToCompetitor.getFirstName());
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
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

	public String getPractiScoreDivisionString() {
		return division;
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

	public String getPractiScorePowerFactorString() {
		return practiScorePowerFactorString;
	}

	public void setPractiScorePowerFactorString(String practiScorePowerFactorString) {
		this.practiScorePowerFactorString = practiScorePowerFactorString;
		this.powerFactor = PowerFactor.valueOf(practiScorePowerFactorString.toUpperCase());
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getSquad() {
		return squad;
	}

	public void setSquad(int squad) {
		this.squad = squad;
	}

	public int getIpscAlias() {
		return ipscAlias;
	}

	public void setIpscAlias(int ipscAlias) {
		this.ipscAlias = ipscAlias;
	}


	public PowerFactor getPowerFactor() {
		return powerFactor;
	}

	public void setPowerFactor(PowerFactor powerFactor) {
		this.powerFactor = powerFactor;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public void setPractiScoreDivisionString(String practiScoreDivisionString) {
		this.division = practiScoreDivisionString;
	}
	
	public void setPractiScoreCategoryString(String categoriesString) {
		this.practiScoreCategoryString = categoriesString;
		if (this.categories == null) this.categories = new ArrayList<String>();
		practiScoreCategoryString = practiScoreCategoryString.replace("\"", "");
		practiScoreCategoryString = practiScoreCategoryString.replace("[", "");
		practiScoreCategoryString = practiScoreCategoryString.replace("]", "");
		practiScoreCategoryString = practiScoreCategoryString.replace(" ", "");
		String[] categoryStrings = practiScoreCategoryString.split(",");
		for (String category : categoryStrings) {
			this.categories.add(category);
		}
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getPractiScoreCategoryString() {
		return practiScoreCategoryString;
	}
	
}
