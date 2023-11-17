package com.authorizationservice.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.authorizationservice.authorization.dto.VaildatingDTO;
import com.authorizationservice.authorization.exceptions.LoginException;
import com.authorizationservice.authorization.model.AuthenticationRequest;
import com.authorizationservice.authorization.model.AuthenticationResponse;
import com.authorizationservice.authorization.model.ChangePasswordModel;
import com.authorizationservice.authorization.service.AppUserDetailsService;
import com.authorizationservice.authorization.util.JwtUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Api(value = "Login and Validation endpoints for Authorization Service")
@CrossOrigin
public class AuthorizationController {

	@Autowired
	private AppUserDetailsService userDetailsService;
	@Autowired
	private JwtUtil jwtTokenUtil;

	private VaildatingDTO vaildatingDTO = new VaildatingDTO();

	@PostMapping("/login")
	@ApiOperation(value = "customerLogin", notes = "takes customer credentials and generates the unique JWT for each customer", httpMethod = "POST", response = ResponseEntity.class)
	public ResponseEntity<Object> createAuthorizationToken(
			@ApiParam(name = "customerLoginCredentials", value = "Login credentials of the Customer") @RequestBody AuthenticationRequest authenticationRequest)
			throws LoginException {
		
		System.out.println(authenticationRequest);
		log.info("BEGIN - [login(customerLoginCredentials)]");
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		log.debug("{}", userDetails);
		
		if (userDetails.getPassword().equals(authenticationRequest.getPassword())) {
			log.info("END - [login(customerLoginCredentials)]");
			return new ResponseEntity<Object>(new AuthenticationResponse(authenticationRequest.getUsername(),
					jwtTokenUtil.generateToken(userDetails), jwtTokenUtil.getCurrentTime(), jwtTokenUtil.getExpirationTime()), HttpStatus.OK);
		} else {
			log.info("END - [login(customerLoginCredentials)]");
			throw new LoginException("Invalid Username or Password");
		}
	}
	
	@PostMapping("/ChangePassword")
	@ApiOperation(value = "ChangePassword", notes = "change password of user", httpMethod = "POST", response = ResponseEntity.class)
	public String changeUserPassword(@RequestBody ChangePasswordModel changePasswordModel, @RequestHeader(name = "Authorization") String tokenDup){
		
		String token = tokenDup.substring(7);
		
		try {
			UserDetails user = userDetailsService.loadUserByUsername(jwtTokenUtil.extractUsername(token));
			
			if(user.getPassword().equals(changePasswordModel.getNewPassword())) {
				return "Old and New Password cannot be same";
			}
			else if(!user.getPassword().equals(changePasswordModel.getOldPassword())) {
				return "Old password does not match with DB password";
			}
			else {
				//AuthenticationRequest authenticationRequest = authRequestrepo.findById(user.getUsername()).orElseThrow();
				AuthenticationRequest authenticationRequest = userDetailsService.getUser(user.getUsername());
				authenticationRequest.setPassword(changePasswordModel.getNewPassword());
				userDetailsService.saveUser(authenticationRequest);
				
				return "Password changed successfully";
			}
		}
		catch(Exception e) {
			return "Forbidden";
		}
	}

	@GetMapping(path = "/validate", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "tokenValidation", notes = "returns boolean after validating JWT", httpMethod = "GET", response = ResponseEntity.class)
	public ResponseEntity<VaildatingDTO> validatingAuthorizationToken(
			@ApiParam(name = "JWT-token", value = "JWT generated for current customer present") @RequestHeader(name = "Authorization") String tokenDup) {
		
		log.info("BEGIN - [validatingAuthorizationToken(JWT-token)]");
		String token = tokenDup.substring(7);
		try {
			UserDetails user = userDetailsService.loadUserByUsername(jwtTokenUtil.extractUsername(token));
			if (Boolean.TRUE.equals(jwtTokenUtil.validateToken(token, user))) {
				log.debug("Token matched is Valid");
				log.info("Token matched is Valid");
				log.info("END - validate()");
				vaildatingDTO.setValidStatus(true);
				return new ResponseEntity<VaildatingDTO>(vaildatingDTO, HttpStatus.OK);
			} else {
				throw new LoginException("Invalid Token");
			}
		} catch (Exception e) {
			log.debug("Invalid token - Bad Credentials Exception");
			log.info("END Exception - validatingAuthorizationToken()");
			vaildatingDTO.setValidStatus(false);
			return new ResponseEntity<VaildatingDTO>(vaildatingDTO, HttpStatus.BAD_REQUEST);
		}
		
	}

	@GetMapping(path = "/health-check")
	public ResponseEntity<String> healthCheck() {
		
		log.info("Authorization Microservice is Up and Running....");
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
	
	//for test
	@GetMapping("/home")
	public String home() {
		return "home";
	}
	
	
}
