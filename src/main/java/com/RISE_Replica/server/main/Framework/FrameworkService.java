package com.RISE_Replica.server.main.Framework;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.github.underscore.lodash.U;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.RISE_Replica.server.Util.ConnectionFactory;
import com.RISE_Replica.server.Util.DbUtil;

@Component
public class FrameworkService {
	
	private PreparedStatement preparedStatment;
	
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

	public int postFiles(String username, String servicetype, String framework,String zdirvar, String oldPassword, MultipartFile file)
			 throws IOException
	{
		 String URL =  "http://vs1.sce.carleton.ca:8080/cdpp/sim/workspaces" ;

			URL = URL.concat("/");
			URL = URL.concat(username);
			URL = URL.concat("/");
			URL = URL.concat(servicetype);
			URL = URL.concat("/");
			URL = URL.concat(framework);
			URL = URL.concat("?zdir=");
			URL = URL.concat(zdirvar);
			ResponseEntity<String> response = null;
			String message = "Files sent!!";
			
			HttpStatus httpStatus = HttpStatus.CREATED;
		    try {	
		    	// AUTHORIZATION
		    	String auth = username + ":" + oldPassword;
		    	byte[] auth64 = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
		    	// HEADERS PREPARATION
		        HttpHeaders headers = new HttpHeaders();
		        headers.add("Content-Type", "application/zip");
		        headers.add("Authorization", "Basic " + new String(auth64, "UTF-8"));
		    	// BODY PREPARATION
		        Resource fileResource = file.getResource();
				LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("file", fileResource);
				// SEND REQUEST
		        RestTemplate restTemplate = new RestTemplate();
		        HttpEntity<byte[]> requestEntity = new HttpEntity<>(file.getBytes(), headers);
		        response = restTemplate.postForEntity(URL, requestEntity, String.class);
		    } 
		    catch (HttpStatusCodeException e) {
		        httpStatus = HttpStatus.valueOf(e.getStatusCode().value());
		        message = e.getResponseBodyAsString();
		    } 
		    catch (Exception e) {
		        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		        message = e.getMessage();
		    }
		    if( response.getStatusCodeValue()==200)
		    { Connection connection = ConnectionFactory.getConnection();
		    	try{
		    		
		    		InputStream initialStream = file.getInputStream();
		    		
		    	//	FileInputStream fis = new FileInputStream(file);
					String sql = "UPDATE framework SET  inputfiles = ? where frameworkname = ? and servicename= ? and workspacename = ?";
					
					PreparedStatement pstmt = connection.prepareStatement(sql);
					pstmt.setBinaryStream(1, initialStream);;
					pstmt.setString(2, framework);
		            pstmt.setString(3, servicetype);
		            pstmt.setString(4, username);
		            
		            

		            pstmt.executeUpdate();
				
				}
		    	catch (SQLException e) {
		            System.out.println("SQLException in get() method");
		            e.printStackTrace();
		        } finally {
		            DbUtil.close(connection);
		        }
					
		    }
			return response.getStatusCodeValue();
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
	
		outStreamWriter.write(xml);
		outStreamWriter.flush();
		outStreamWriter.close();
		outStream.close();

		

		int responseCode = con.getResponseCode();
		System.out.println("PUT Response Code :: " + responseCode);

		if (responseCode == 200)//update
		{ Connection connection = ConnectionFactory.getConnection();
			try{
				String sql = "UPDATE framework SET  xmlstring = ? where frameworkname = ? and servicename= ? and workspacename = ?";
				
				PreparedStatement pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, xml);
				pstmt.setString(2, framework);
	            pstmt.setString(3, servicetype);
	            pstmt.setString(4, username);
	            
	            

	            pstmt.executeUpdate();
			
			}
			catch (SQLException e) {
	            System.out.println("SQLException in get() method");
	            e.printStackTrace();
	        } finally {
	            DbUtil.close(connection);
	        }
				
		}
		else if (responseCode == 201)//create
		{ Connection connection = ConnectionFactory.getConnection();
			try{
				String sql = "INSERT INTO framework(frameworkname,servicename, workspacename, xmlstring) VALUES (?, ?, ?, ?)";
				
				PreparedStatement pstmt = connection.prepareStatement(sql);
	            pstmt.setString(1, framework);
	            pstmt.setString(2, servicetype);
	            pstmt.setString(3, username);
	            pstmt.setString(4, xml);
	            

	            pstmt.executeUpdate();
			
			}
			catch (SQLException e) {
	            System.out.println("SQLException in get() method");
	            e.printStackTrace();
	        } finally {
	            DbUtil.close(connection);
	        }
			
		}
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
