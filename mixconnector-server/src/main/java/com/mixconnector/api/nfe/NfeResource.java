package com.mixconnector.api.nfe;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mixconnector.domain.NfeService;
import com.mixconnector.domain.NfeStatusReport;
import com.mixconnector.entity.NfeEntity;
import com.mixconnector.queue.MongoQueue;

@RestController
public class NfeResource {

	@Autowired
	private MongoQueue mongoQueue;

	@Autowired
	private NfeService nfeService;

	@PostMapping("/api/nfe")
	public ResponseEntity<NfeResponse> post(@RequestBody NfeRequest nfeRequest) {
		final UUID lotId = nfeService.saveAll(nfeRequest.toModel());
		mongoQueue.consume();
		final NfeResponse nfeResponse = new NfeResponse();
		nfeResponse.setLotId(lotId);

		return ResponseEntity.accepted()
							 .body(nfeResponse);
	}

	@GetMapping("/api/nfe")
	public ResponseEntity<NfeResponse> get(@RequestParam(name = "key", required = false) String key, @RequestParam(name = "lotId", required = false) UUID lotId) {
		final List<NfeEntity> nfeEntities = nfeService.findByKeyOrLotId(key, lotId);
		NfeResponse nfeResponse = new NfeResponse();
		nfeResponse.setLotId(lotId);
		nfeResponse.setKeys(nfeEntities.stream()
									   .map(NfeEntity::toString)
									   .collect(toList()));
		return ResponseEntity.ok(nfeResponse);
	}

	@GetMapping("/api/nfe/status")
	public ResponseEntity<List<NfeStatusReport>> status() {
		final List<NfeStatusReport> statusReports = nfeService.getAllGroupedByStatus();
		return ResponseEntity.ok(statusReports);
	}

}
