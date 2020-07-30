package com.RISE_Replica.server.main.Framework;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FrameworkController {
	@Autowired  
	private FrameworkService service; 

	/*
	 *  View Framework
	 */
	@GetMapping(path = "/workspaces/{username}/{servicetype}/{framework}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getFramework(@PathVariable String username,@PathVariable String servicetype,@PathVariable String framework) throws IOException {
		JSONObject frameworkJson = service.getFramework(username,servicetype,framework);
		return ResponseEntity.status(HttpStatus.OK).body(frameworkJson.toString());

	}
	@PutMapping(path = "/workspaces/{username}/{servicetype}/{framework}/{oldPassword}")
	public String updateFramework(@PathVariable String username, @PathVariable String servicetype,@PathVariable String framework, @PathVariable String oldPassword,
			@RequestBody String accountProperties) throws IOException {
		
		int x = service.updateFramework(username, servicetype,framework, oldPassword, accountProperties);
		if (x == 200)
			return "Status = 200 : Framework Updated";

		else if (x == 201)
			return "Status = 201 : Framework Created";

		else
			return "Status : " + Integer.toString(x) + "\n Problem occured while updating Framework";
	}
	@PostMapping(path = "/workspaces/{username}/{servicetype}/{framework}/{oldPassword}")
	public String addFramework(@PathVariable String username, @PathVariable String servicetype,@PathVariable String framework, @PathVariable String oldPassword,
			@RequestBody String accountProperties) throws IOException {
		int x = service.addFramework(username, servicetype,framework, oldPassword, accountProperties);
		if (x == 200)
			return "Status = 200 : Framework Updated";

		else if (x == 201)
			return "Status = 201 : Framework Created";

		else
			return "Status : " + Integer.toString(x) + "\n Problem occured while updating Framework";
	}
	@DeleteMapping(path = "/workspaces/{username}/{servicetype}/{framework}/{oldPassword}")
	public String deleteFramework(@PathVariable String username, @PathVariable String servicetype,@PathVariable String framework, @PathVariable String oldPassword) throws IOException {
		int x = service.deleteFramework(username, servicetype,framework, oldPassword);
		if (x == 200)
			return "Status = 200 : Framework Deleted";

		else
			return "Status : " + Integer.toString(x) + "\n Problem occured while deleting Framework";
	}
	
	
}
