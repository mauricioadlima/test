package com.nfespy.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckResource {

	@RequestMapping(method = RequestMethod.GET, path = "/check")
	public ResponseEntity check() {
		return ResponseEntity.ok("OK");
	}

}
