package fi.ipsc_result_server.domain.ResultData;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MatchResultDataLine {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
