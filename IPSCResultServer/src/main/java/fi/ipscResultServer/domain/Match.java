package fi.ipscResultServer.domain;
import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Match {

	private Long id;
	
	@JsonProperty("match_id")
	private String practiScoreId;
	
	private User user;
	
	private Long userId;
	
	private boolean uploadedByAdmin = false;
	
	@JsonProperty("match_name")
	private String name;
	
	private MatchStatus status = MatchStatus.SCORING;
	
	@JsonProperty("match_level")
	private String level;

	@JsonProperty("match_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Calendar date;

	@JsonProperty("match_cats")
	private List<String> divisions;
	
	private List<String> divisionsWithResults;
		
	@JsonProperty("match_stages")
	private List<Stage> stages;
	
	@JsonProperty("match_shooters")
	private List<Competitor> competitors; 

	public Match() { }
	
	public Match(Long id, String practiScoreId, String name, 
			String level, Calendar date, MatchStatus status, User user, boolean uploadedByAdmin) { 
		this.id = id;
		this.practiScoreId = practiScoreId;
		this.name = name;
		this.level = level;
		this.status = status;
		this.date = date;
		this.user = user;
		this.uploadedByAdmin = uploadedByAdmin;
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.trim();
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
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

	public MatchStatus getStatus() {
		return status;
	}

	public void setStatus(MatchStatus status) {
		this.status = status;
	}

	public List<String> getDivisionsWithResults() {
		return divisionsWithResults;
	}

	public void setDivisionsWithResults(List<String> divisionsWithResults) {
		this.divisionsWithResults = divisionsWithResults;
	}

	public List<String> getDivisions() {
		return divisions;
	}

	public void setDivisions(List<String> divisions) {
		this.divisions = divisions;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isUploadedByAdmin() {
		return uploadedByAdmin;
	}

	public void setUploadedByAdmin(boolean uploadedByAdmin) {
		this.uploadedByAdmin = uploadedByAdmin;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
