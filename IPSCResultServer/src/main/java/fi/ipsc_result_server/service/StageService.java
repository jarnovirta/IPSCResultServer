package fi.ipsc_result_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ipsc_result_server.domain.Stage;
import fi.ipsc_result_server.repository.StageRepository;

@Service
public class StageService {

	@Autowired
	StageRepository stageRepository;
	
	public Stage getOne(String id) {
		return stageRepository.getOne(id);
	}
}
