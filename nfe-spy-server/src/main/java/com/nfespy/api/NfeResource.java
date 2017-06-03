package com.nfespy.api;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nfespy.model.Nfe;
import com.nfespy.queue.MongoQueue;
import com.nfespy.service.NfeService;

@RestController("/api/nfe")
public class NfeResource {

	@Autowired
	private MongoQueue mongoQueue;

	@Autowired
	private NfeService nfeService;

	@PostMapping
	public ResponseEntity<NfeResponse> post(@RequestBody NfeRequest nfeRequest) {
		final UUID lotId = nfeService.saveAll(nfeRequest.toModel());
		mongoQueue.consume();
		final NfeResponse nfeResponse = new NfeResponse();
		nfeResponse.setLotId(lotId);

		return ResponseEntity.accepted()
							 .body(nfeResponse);
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<NfeResponse> get(@RequestParam(name = "key", required = false) String key,
							  @RequestParam(name = "lotId", required = false) UUID lotId) {

		final List<Nfe> nfes = nfeService.findByKeyOrLotId(key, lotId);
		NfeResponse nfeResponse = new NfeResponse();
		nfeResponse.setLotId(lotId);
		nfeResponse.setKeys(nfes.stream().map(Nfe::getKey).collect(toList()));
		return ResponseEntity.ok(nfeResponse);
	}
}
