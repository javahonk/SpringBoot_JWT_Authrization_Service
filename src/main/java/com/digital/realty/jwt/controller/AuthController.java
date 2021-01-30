package com.digital.realty.jwt.controller;

import com.digital.realty.jwt.model.RequestModel;
import com.digital.realty.jwt.model.ResponseModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthController {

	@RequestMapping({ "/validate" })
	public String validate() {
		return "User validation is successful";
	}
}
