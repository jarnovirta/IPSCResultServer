package fi.ipsc_result_server.domain;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "IPSCMatch")
@UuidGenerator(name="MATCH_ID_GEN")
public class Match implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@JsonProperty("match_id")
	@GeneratedValue(generator="MATCH_ID_GEN")
	@Column(columnDefinition = "BINARY(128)")
	private UUID uuid;
	
	@JsonProperty("match_name")
	@Column(nullable = false)
	private String name;
	
	@JsonProperty("match_level")
	private String level;

	@JsonProperty("match_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Calendar date;

	@JsonProperty("match_modifieddate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Calendar modifiedDate;

	@JsonProperty("match_cats")
	@Column(nullable = false)
	private List<String> divisions;
	
	@JsonProperty("match_stages")
	@OneToMany(cascade = CascadeType.PERSIST)
	private List<Stage> stages;
	
	@JsonProperty("match_shooters")
	@OneToMany(cascade = CascadeType.PERSIST)
	private List<Competitor> competitors; 


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public Calendar getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Calendar modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public List<String> getDivisions() {
		return divisions;
	}

	public void setDivisions(List<String> divisions) {
		this.divisions = divisions;
	}

	public List<Stage> getStages() {
		return stages;
	}

	public void setStages(List<Stage> stages) {
		this.stages = stages;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public List<Competitor> getCompetitors() {
		return competitors;
	}

	public void setCompetitors(List<Competitor> competitors) {
		this.competitors = competitors;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
