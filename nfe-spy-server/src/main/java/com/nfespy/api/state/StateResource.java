package com.nfespy.api.state;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nfespy.entity.EstadoEntity;
import com.nfespy.repository.EstadoRepository;

@RestController
public class StateResource {

	@Autowired
	private EstadoRepository estadoRepository;

	@PostMapping("/api/config/state")
	public StateRequestResponse post(@RequestBody StateRequestResponse stateRequestResponse) {
		final EstadoEntity estadoEntity = estadoRepository.save(stateRequestResponse.toModel());
		return new StateRequestResponse(estadoEntity);
	}

	@GetMapping("/api/config/state")
	public ResponseEntity get(@RequestParam(name = "state", required = false) String stateId) {
		if (stateId != null) {
			final EstadoEntity estadoEntity = estadoRepository.findOne(stateId);
			return ResponseEntity.ok(new StateRequestResponse(estadoEntity));
		}
		final List<EstadoEntity> estadoEntities = estadoRepository.findAll();
		return ResponseEntity.ok(estadoEntities.stream()
											   .map(StateRequestResponse::new)
											   .collect(toList()));
	}

}
