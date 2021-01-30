package com.digital.realty.jwt.controller;

import com.digital.realty.jwt.model.RequestModel;
import com.digital.realty.jwt.model.ResponseModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class RequestController {

	@RequestMapping({ "/validate" })
	public String validate() {
		return "User is validation is successful";
	}

	@RequestMapping("/testGet")
	public String test() {
		return "My testing is successful";
	}

	@RequestMapping(value = "/testPost", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody RequestModel authenticationRequest) throws Exception {
		return ResponseEntity.ok(new ResponseModel("Test post passed authentication"));
	}

	/*@RequestMapping(value = "/health", method = RequestMethod.POST)
	public String health() {
		return "User is validation is successful";
	}

	@RequestMapping(value = "/health")
	public String health2() {
		return "User is validation is successful";
	}*/

}
