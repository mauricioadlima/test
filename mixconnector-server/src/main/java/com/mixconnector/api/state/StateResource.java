package com.mixconnector.api.state;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mixconnector.entity.StateEntity;
import com.mixconnector.repository.StateRepository;

@RestController
public class StateResource {

	@Autowired
	private StateRepository stateRepository;

	@PostMapping("/api/config/state")
	public StateRequestResponse post(@RequestBody StateRequestResponse stateRequestResponse) {
		final StateEntity stateEntity = stateRepository.save(stateRequestResponse.toModel());
		return new StateRequestResponse(stateEntity);
	}

	@GetMapping("/api/config/state")
	public ResponseEntity get(@RequestParam(name = "state", required = false) String stateId) {
		if (stateId != null) {
			final StateEntity stateEntity = stateRepository.findOne(stateId);
			return ResponseEntity.ok(new StateRequestResponse(stateEntity));
		}
		final List<StateEntity> estadoEntities = stateRepository.findAll();
		return ResponseEntity.ok(estadoEntities.stream()
											   .map(StateRequestResponse::new)
											   .collect(toList()));
	}

}
