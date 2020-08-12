package com.RISE_Replica.server.main.Framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;

import com.github.underscore.lodash.U;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FrameworkService {
	public JSONObject getFramework(String username, String servicetype, String framework) throws IOException {
		 String URL =  "http://vs1.sce.carleton.ca:8080/cdpp/sim/workspaces" ;
		 	URL = URL.concat("/");
			URL = URL.concat(username);
			URL = URL.concat("/");
			URL = URL.concat(servicetype);
			URL = URL.concat("/");
			URL = URL.concat(framework);
			URL = URL.concat("?fmt=xml");

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

	public int postFiles(String username, String servicetype, String framework, String oldPassword, MultipartFile file)
			 throws IOException
	{
		 String URL =  "http://vs1.sce.carleton.ca:8080/cdpp/sim/workspaces" ;

		
	
		URL = URL.concat("/");
		URL = URL.concat(username);
		URL = URL.concat("/");
		URL = URL.concat(servicetype);
		URL = URL.concat("/");
		URL = URL.concat(framework);
		URL = URL.concat("?zdir=barber");
		
		URL obj = new URL(URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");

		con.setRequestProperty("Content-Type", "application/zip");
		  String auth = username + ":" + oldPassword;
		  byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
		  String authValue = "Basic " + new String(encodedAuth);

		con.setRequestProperty("Authorization", authValue);
		
		con.setDoOutput(true);

		 LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		    String response;
		    HttpStatus httpStatus = HttpStatus.CREATED;

		    try {
		        
		            if (!file.isEmpty()) {
		                map.add("files", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));
		            }
		            System.out.print(map);

		        HttpHeaders headers = new HttpHeaders();
		        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		        RestTemplate restTemplate = new RestTemplate();

		        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
		        response = restTemplate.postForObject(URL, requestEntity, String.class);

		    } catch (HttpStatusCodeException e) {
		        httpStatus = HttpStatus.valueOf(e.getStatusCode().value());
		        response = e.getResponseBodyAsString();
		        System.out.println(response);
		    } catch (Exception e) {
		        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		        response = e.getMessage();
		    }
		    System.out.println(response);
		

		int responseCode = con.getResponseCode();

		return responseCode;
	}

	

	public int deleteFramework(String username, String servicetype, String framework, String oldPassword) 
			 throws IOException
	{
		 String URL =  "http://vs1.sce.carleton.ca:8080/cdpp/sim/workspaces" ;

			URL = URL.concat("/");
			URL = URL.concat(username);
			URL = URL.concat("/");
			URL = URL.concat(servicetype);
			URL = URL.concat("/");
			URL = URL.concat(framework);
			URL obj = new URL(URL);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("DELETE");
			//con.setRequestProperty("Accept", "application/xml");
			con.setRequestProperty("Content-Type", "text/xml");
			 String auth = username + ":" + oldPassword;
			  byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
			  String authValue = "Basic " + new String(encodedAuth);

			con.setRequestProperty("Authorization", authValue);
			// For POST only - START
			con.setDoOutput(true);
			OutputStream outStream = con.getOutputStream();
			OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "UTF-8");
			outStreamWriter.flush();
			outStreamWriter.close();
			outStream.close();
			// For POST only - END

			int responseCode = con.getResponseCode();
			System.out.println("DELETE Response Code :: " + responseCode);

			if (responseCode == HttpURLConnection.HTTP_OK) { //success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// print result
			//	System.out.println(response.toString());
			
			} else {
				System.out.println("DELETE request not worked");
			}
			return responseCode;
	}

	public int updateFramework(String username, String servicetype, String framework, String oldPassword, String accountProperties) 
			 throws IOException
	{ 
		 String URL =  "http://vs1.sce.carleton.ca:8080/cdpp/sim/workspaces" ;

		  String xml = U.jsonToXml(accountProperties.toString());
	
		URL = URL.concat("/");
		URL = URL.concat(username);
		URL = URL.concat("/");
		URL = URL.concat(servicetype);
		URL = URL.concat("/");
		URL = URL.concat(framework);
		
		URL obj = new URL(URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("PUT");

		con.setRequestProperty("Content-Type", "text/xml");
		  String auth = username + ":" + oldPassword;
		  byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
		  String authValue = "Basic " + new String(encodedAuth);

		con.setRequestProperty("Authorization", authValue);
		
		con.setDoOutput(true);
		OutputStream outStream = con.getOutputStream();
		OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "UTF-8");
		//String requestedXml= "<Account><Password>AAAA</Password><Admin>false</Admin></Account>";
		outStreamWriter.write(xml);
		outStreamWriter.flush();
		outStreamWriter.close();
		outStream.close();

		

		int responseCode = con.getResponseCode();
		System.out.println("PUT Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

//			System.out.println(response.toString());
		} else {
			System.out.println("PUT request not worked");
		}
		return responseCode;
	}

	
}
