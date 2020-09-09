package com.RISE_Replica.server.main.simulationResource;

import java.io.BufferedReader;
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
import java.sql.SQLException;
import java.util.Vector;
import java.util.zip.ZipInputStream;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Component;

import com.RISE_Replica.server.Util.ConnectionFactory;
import com.RISE_Replica.server.Util.DbUtil;
import com.github.underscore.lodash.U;

@Component
public class SimulationResourceService {
	
	public JSONObject getSimulation(String username, String servicetype, String framework) throws IOException {
		 String URL =  "http://vs1.sce.carleton.ca:8080/cdpp/sim/workspaces" ;
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

	public int startSimulation(String username, String servicetype, String framework, String oldPassword) throws IOException {
		 String URL =  "http://vs1.sce.carleton.ca:8080/cdpp/sim/workspaces" ;

		  String xml = "NULL";
	
		URL = URL.concat("/");
		URL = URL.concat(username);
		URL = URL.concat("/");
		URL = URL.concat(servicetype);
		URL = URL.concat("/");
		URL = URL.concat(framework);
		URL = URL.concat("/simulation");

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

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			System.out.println(response.toString());
		} else {
			System.out.println("PUT request not worked");
		}
		return responseCode;
	}

	
	

	public int addMessage(String username, String servicetype, String framework, String oldPassword,
			String accountProperties)  throws IOException {
		 String URL =  "http://vs1.sce.carleton.ca:8080/cdpp/sim/workspaces" ;

		  String xml = U.jsonToXml(accountProperties.toString());
	
		URL = URL.concat("/");
		URL = URL.concat(username);
		URL = URL.concat("/");
		URL = URL.concat(servicetype);
		URL = URL.concat("/");
		URL = URL.concat(framework);
		URL = URL.concat("/simulation");
		
		URL obj = new URL(URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");

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
		
		System.out.println("POST Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			System.out.println(response.toString());
		} else {
			System.out.println("POST request not worked");
		}
		return responseCode;
	}

	


	public int stopSimulation(String username, String servicetype, String framework, String oldPassword)  throws IOException {
		
		 String URL =  "http://vs1.sce.carleton.ca:8080/cdpp/sim/workspaces" ;

			URL = URL.concat("/");
			URL = URL.concat(username);
			URL = URL.concat("/");
			URL = URL.concat(servicetype);
			URL = URL.concat("/");
			URL = URL.concat(framework);
			URL = URL.concat("/simulation");
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

	
	/*
	 * Simulation Results -  Get & Delete
	 */
	public String getSimResults(String username, String servicetype, String framework)  throws IOException {
		 Connection connection = ConnectionFactory.getConnection();
		 String URL =  "http://vs1.sce.carleton.ca:8080/cdpp/sim/workspaces" ;
		 	URL = URL.concat("/");
			URL = URL.concat(username);
			URL = URL.concat("/");
			URL = URL.concat(servicetype);
			URL = URL.concat("/");
			URL = URL.concat(framework);
			URL = URL.concat("/results");
			 URL obj = new URL(URL);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");
				con.setRequestProperty("Content-Type", "application/zip");
				int responseCode = con.getResponseCode();
				System.out.println("GET Response Code :: " + con.getResponseCode());
				if (responseCode == HttpURLConnection.HTTP_OK) { // success
				//	InputStreamReader filestream = new InputStreamReader(con.getInputStream());
					InputStream is = con.getInputStream();
					
					try{
			    		
						String sql = "UPDATE framework SET  outputfiles = ? where frameworkname = ? and servicename= ? and workspacename = ?";
						
						PreparedStatement pstmt = connection.prepareStatement(sql);
						pstmt.setBinaryStream(1, is);;
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
					
					return URL;
				} else {
				
					
					return "GET request not worked";
				}
	
	}

	public int deleteSimResults(String username, String servicetype, String framework, String oldPassword) throws IOException {
		 String URL =  "http://vs1.sce.carleton.ca:8080/cdpp/sim/workspaces" ;

			URL = URL.concat("/");
			URL = URL.concat(username);
			URL = URL.concat("/");
			URL = URL.concat(servicetype);
			URL = URL.concat("/");
			URL = URL.concat(framework);
			URL = URL.concat("/results");
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
	/*
	 * Simulation Debug - Get & Delete 
	 */
	
	public String getDebugResults(String username, String servicetype, String framework)  throws IOException {
		 Connection connection = ConnectionFactory.getConnection();
		 String URL =  "http://vs1.sce.carleton.ca:8080/cdpp/sim/workspaces" ;
		 	URL = URL.concat("/");
			URL = URL.concat(username);
			URL = URL.concat("/");
			URL = URL.concat(servicetype);
			URL = URL.concat("/");
			URL = URL.concat(framework);
			URL = URL.concat("/debug");

			 URL obj = new URL(URL);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");
				con.setRequestProperty("Content-Type", "application/zip");
				int responseCode = con.getResponseCode();
				System.out.println("GET Response Code :: " + con.getResponseCode());
				if (responseCode == HttpURLConnection.HTTP_OK) { // success
				//	InputStreamReader filestream = new InputStreamReader(con.getInputStream());
					InputStream is = con.getInputStream();
					
					try{
			    		
						String sql = "UPDATE framework SET  debugfiles = ? where frameworkname = ? and servicename= ? and workspacename = ?";
						
						PreparedStatement pstmt = connection.prepareStatement(sql);
						pstmt.setBinaryStream(1, is);;
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
					
					return URL;
				} else {
				
					
					return "GET request not worked";
				}
	
	}

	public int deleteDebugResults(String username, String servicetype, String framework, String oldPassword) throws IOException {
		 String URL =  "http://vs1.sce.carleton.ca:8080/cdpp/sim/workspaces" ;

			URL = URL.concat("/");
			URL = URL.concat(username);
			URL = URL.concat("/");
			URL = URL.concat(servicetype);
			URL = URL.concat("/");
			URL = URL.concat(framework);
			URL = URL.concat("/debug");
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

	public String getAllInfo(String username, String servicetype, String framework) throws IOException {
		 Connection connection = ConnectionFactory.getConnection();

		getDebugResults(username,servicetype,framework);
		getSimResults(username,servicetype,framework);
		
		 ResultSet results = null;
	        try {
	        	
	            connection = ConnectionFactory.getConnection();
	            String sql = "SELECT * FROM framework where  frameworkname = ? and servicename= ? and workspacename = ?";

						
						PreparedStatement pstmt = connection.prepareStatement(sql);
			
						pstmt.setString(1, framework);
			            pstmt.setString(2, servicetype);
			            pstmt.setString(3, username);

	            results = pstmt.executeQuery();
//	            while (results.next())
//	            {
//	            	//
//	            }
	            
	        } catch (SQLException e) {
	            System.out.println("SQLException in get() method");
	            e.printStackTrace();
	        } finally {
	            DbUtil.close(connection);
	        }

	
return null;
}
}