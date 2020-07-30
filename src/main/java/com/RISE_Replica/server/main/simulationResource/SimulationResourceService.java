package com.RISE_Replica.server.main.simulationResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Component;

@Component
public class SimulationResourceService {

	public JSONObject getState(String username, String servicetype, String framework) throws IOException {
		 String URL =  "http://100.25.4.105:8080/cdpp/sim/workspaces" ;
		 	URL = URL.concat("/");
			URL = URL.concat(username);
			URL = URL.concat("/");
			URL = URL.concat(servicetype);
			URL = URL.concat("/");
			URL = URL.concat(framework);
			URL = URL.concat("/simulation?fmt=xml");

			 URL obj = new URL(URL);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");

				int responseCode = con.getResponseCode();
				System.out.println("GET Response Code :: " + con.getResponseCode());
				if (responseCode == HttpURLConnection.HTTP_OK) { // success
					BufferedReader in = new BufferedReader(new InputStreamReader(
							con.getInputStream()));
					String inputLine;
					StringBuffer response = new StringBuffer();
					
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					in.close();
					
					JSONObject soapDatainJsonObject = XML.toJSONObject(response.toString());
		
			//		System.out.println(response.toString());
					return soapDatainJsonObject;
				} else {
				
					JSONObject JsonObject = XML.toJSONObject("GET request not worked");
					return JsonObject;
				}
	
	}

	public int updateState(String username, String servicetype, String framework, String oldPassword) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int addState(String username, String servicetype, String framework, String oldPassword,
			String accountProperties)  throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int deleteState(String username, String servicetype, String framework, String oldPassword)  throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	
	/*
	 * Simulation Results -  Get & Delete
	 */
	public String getSimResults(String username, String servicetype, String framework)  throws IOException {
		 String URL =  "http://100.25.4.105:8080/cdpp/sim/workspaces" ;
		 	URL = URL.concat("/");
			URL = URL.concat(username);
			URL = URL.concat("/");
			URL = URL.concat(servicetype);
			URL = URL.concat("/");
			URL = URL.concat(framework);
			URL = URL.concat("/results?fmt=xml");
System.out.print(URL);
			 URL obj = new URL(URL);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");

				return URL;
	
	}

	public int deleteSimResults(String username, String servicetype, String framework, String oldPassword) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}
	/*
	 * Simulation Debug - Get & Delete 
	 */
	
	public String getDebugResults(String username, String servicetype, String framework)  throws IOException {
		 String URL =  "http://100.25.4.105:8080/cdpp/sim/workspaces" ;
		 	URL = URL.concat("/");
			URL = URL.concat(username);
			URL = URL.concat("/");
			URL = URL.concat(servicetype);
			URL = URL.concat("/");
			URL = URL.concat(framework);
			URL = URL.concat("/debug?fmt=xml");

			 URL obj = new URL(URL);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");

				int responseCode = con.getResponseCode();
				System.out.println("GET Response Code :: " + con.getResponseCode());
				return URL;
	
	}

	public int deleteDebugResults(String username, String servicetype, String framework, String oldPassword) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

}
