package com.poc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.poc.exception.AuthenticationException;
import com.poc.model.AuthenticationRequest;
import com.poc.model.AuthenticationResponse;
import com.poc.util.JWTUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class DemoController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@RequestMapping("/")
	public String welcome() {
		return "Welcome...!";
	}

	@RequestMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String admin() {
		return "Welcome...Admin!";
	}
	
	@RequestMapping("/user")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public String user() {
		return "Welcome...User!";
	}
	
	@RequestMapping("/userdelete")
	@PreAuthorize("hasAuthority('DELETE_USER')")
	public String userDelete() {
		return "User DELETE API - Has access to ADMIN!";
	}
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		} catch (DisabledException e) {
		    throw new AuthenticationException("USER_DISABLED", e);
	    } catch (BadCredentialsException e) {
	    	throw new AuthenticationException("INVALID_CREDENTIALS", e);
	    } catch (Exception e) {
	    	throw new Exception("INTERNAL_SERVER_ERROR", e);
	    }

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
}
