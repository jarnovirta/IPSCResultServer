package fi.ipscResultServer.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

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
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Match match;
	
	@JsonProperty("sh_uid")
	@Column(length = 36)
	private String practiScoreId;
	
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
	private String ipscAlias;
	
	@JsonProperty("sh_dvp")
	@Column(nullable = false)
	private String division;
	
	@ElementCollection
	private List<String> categories;
	
	@JsonProperty("sh_ctgs")
	@Transient
	private String practiScoreCategoryString;
	
	@JsonProperty("sh_dq")
	@Column(nullable = false)
	private boolean disqualified;
	
	@JsonProperty("sh_team")
	private String team;
	
	@JsonProperty("sh_del")
	@Transient
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
	    int lastNameComparison = this.lastName.toLowerCase().compareTo(compareToCompetitor.getLastName().toLowerCase());
	    if (lastNameComparison != EQUAL) return lastNameComparison;
	    return this.firstName.compareTo(compareToCompetitor.getFirstName());
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPractiScoreId() {
		return practiScoreId;
	}

	public void setPractiScoreId(String practiScoreId) {
		this.practiScoreId = practiScoreId;
	}

	public Match getMatch() {
		return match;
	}



	public void setMatch(Match match) {
		this.match = match;
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

	public boolean isDisqualified() {
		return disqualified;
	}

	public void setDisqualified(boolean disqualified) {
		this.disqualified = disqualified;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
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

	public String getIpscAlias() {
		return ipscAlias;
	}

	public void setIpscAlias(String ipscAlias) {
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
