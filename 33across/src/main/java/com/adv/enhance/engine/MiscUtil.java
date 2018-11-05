package com.adv.enhance.engine;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MiscUtil {
	public static final Logger logger = LoggerFactory.getLogger(MiscUtil.class);
	
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	
	/**
	 * Synchronous GET with OkHttp
	 * @param url
	 */
	public synchronized static String sendGet(String url) {
	
		OkHttpClient client= new OkHttpClient();
		Request req = new Request.Builder().url(url).get().build();
		Response response;
		try {
			response = client.newCall(req).execute();
			if(!response.isSuccessful()) {
				throw new IOException(response.message());
			}
			return response.body().string();
		} catch (IOException e) {
			logger.error("Failed to execute GET '" + url + "'", e);
			throw new RuntimeException("Failed to execute GET '" + url + "'", e);
		}
	}
	

	public synchronized static String sendPost(String url, String content) {
		
		OkHttpClient client= new OkHttpClient();
		RequestBody reqBody = RequestBody.create(JSON, content);
		Request req = new Request.Builder().url(url).post(reqBody).build();
		Response response;
		try {
			response = client.newCall(req).execute();
			if(!response.isSuccessful()) {
				throw new IOException(response.message());
			}
			return response.body().string();
		} catch (IOException e) {
			logger.error("Failed to execute POST '" + url + "'", e);
			throw new RuntimeException("Failed to execute POST '" + url + "' with content : \n " + content, e);
		}
	}
	
	public synchronized static JsonNode createEmptyJson(String fieldName) throws JsonParseException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.createObjectNode().putObject(fieldName);   
	}

	public synchronized static JsonNode stringToJson(String jsonString) throws JsonParseException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
	    JsonFactory factory = mapper.getFactory();
	    JsonParser parser = factory.createParser(jsonString);
	    return mapper.readTree(parser);	    
	}
	
	public synchronized static void addJson(JsonNode parent, String key, JsonNode value) {
		
		((ObjectNode) parent).set(key, value);
	}
	
	public synchronized static void addJson(JsonNode parent, String key, String value) {
		
		((ObjectNode) parent).put(key, value);
	}
	
}
