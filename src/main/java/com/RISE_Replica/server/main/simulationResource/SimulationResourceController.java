package com.RISE_Replica.server.main.simulationResource;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class SimulationResourceController {
	@Autowired
	private SimulationResourceService service;
	
	@GetMapping(path = "/workspaces/{username}/{servicetype}/{framework}/simulation", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getSimulation(@PathVariable String username,@PathVariable String servicetype,@PathVariable String framework) throws IOException {
		JSONObject res = service.getSimulation(username,servicetype,framework);
		return ResponseEntity.status(HttpStatus.OK).body(res.toString());

	}
	@PutMapping(path = "/workspaces/{username}/{servicetype}/{framework}/{oldPassword}/simulation")
	public String startSimulation(@PathVariable String username, @PathVariable String servicetype,@PathVariable String framework, 
			@PathVariable String oldPassword) throws IOException {
		int x = service.startSimulation(username, servicetype,framework, oldPassword);
		if (x == 202)
			return "Status = 202 :Simulation Started";

		else
			return "Status : " + Integer.toString(x) + "\n Problem occured";
	}
	@PostMapping(path = "/workspaces/{username}/{servicetype}/{framework}/{oldPassword}/simulation")
	public String addMessage(@PathVariable String username, @PathVariable String servicetype,@PathVariable String framework, @PathVariable String oldPassword,
			@RequestBody String accountProperties) throws IOException {
		int x = service.addMessage(username, servicetype,framework, oldPassword, accountProperties);
		if (x == 202)
			return "Status = 202 : Message posted";

	
		else
			return "Status : " + Integer.toString(x) + "\n Problem occured";
	}
	@DeleteMapping(path = "/workspaces/{username}/{servicetype}/{framework}/{oldPassword}/simulation")
	public String stopSimulation(@PathVariable String username, @PathVariable String servicetype,@PathVariable String framework, 
			@PathVariable String oldPassword) throws IOException {
		int x = service.stopSimulation(username, servicetype,framework, oldPassword);
		if (x == 202)
			return "Status = 202 : Simulation Stoped";

		else
			return "Status : " + Integer.toString(x) + "\n Problem occured";
	}
	
	/*
	 * Simulation Results -  Get & Delete
	 */
	@GetMapping(path = "/workspaces/{username}/{servicetype}/{framework}/results")
	public String getSimResults(@PathVariable String username,@PathVariable String servicetype,@PathVariable String framework) throws IOException {
		String x = service.getSimResults(username,servicetype,framework);
		return x;

	}
	@DeleteMapping(path = "/workspaces/{username}/{servicetype}/{framework}/{oldPassword}/results")
	public String deleteSimResults(@PathVariable String username, @PathVariable String servicetype,@PathVariable String framework, 
			@PathVariable String oldPassword) throws IOException {
		int x = service.deleteSimResults(username, servicetype,framework, oldPassword);
		if (x == 200)
			return "Status = 200 : Simulation Results Deleted";

		else
			return "Status : " + Integer.toString(x) + "\n Problem occured";
	}
	/*
	 * Simulation Debug - Get & Delete 
	 */
	@GetMapping(path = "/workspaces/{username}/{servicetype}/{framework}/debug")
	public String getDebugResults(@PathVariable String username,@PathVariable String servicetype,@PathVariable String framework) throws IOException {
		String x = service.getDebugResults(username,servicetype,framework);
		return x;

	}
	@DeleteMapping(path = "/workspaces/{username}/{servicetype}/{framework}/{oldPassword}/debug")
	public String deleteDebugResults(@PathVariable String username, @PathVariable String servicetype,@PathVariable String framework, 
			@PathVariable String oldPassword) throws IOException {
		int x = service.deleteDebugResults(username, servicetype,framework, oldPassword);
		if (x == 200)
			return "Status = 200 : Simulation Debug Results Deleted";

		else
			return "Status : " + Integer.toString(x) + "\n Problem occured";
	}
}
