import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.equalTo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class AdvReqEnhancementTest {
	public static final Logger LOGGER = LoggerFactory.getLogger(AdvReqEnhancementTest.class);

	private static RequestSpecification spec;
	private static final String URL = "/api/enhance/site";
	private static String positiveReqContentFile = "positiveReqContent.json";
	private static String negativeReqContentFile = "negativeReqContent.json";
	private static final String ENHANCED_SITE_SCHEMA = "site-schema.json";
	
	
	public static void setup() {		
		spec = new RequestSpecBuilder()
	            .setContentType(ContentType.JSON)
	            .setBaseUri("http://localhost:8080")
	            .addFilter(new ResponseLoggingFilter())
	            .addFilter(new RequestLoggingFilter())
	            .build();
	    
	}
	
	/**
	 * Tests '/api/enhance/site/' with valid request 
	 */
	public void validSiteTest() {
		ClassLoader classLoader = getClass().getClassLoader();
		Path path = null;
		String content = null;
		try {
			path = Paths.get(classLoader.getResource(positiveReqContentFile).toURI());
			content = new String(Files.readAllBytes(path));
			given()
				.spec(spec)
				.body(content)
			.expect()
				.statusCode(200)
			.when()
				.post(URL)
			.then()
				.body("site.id", equalTo("foo123"))
				.body("site.publisher.id", equalTo("ksjdf9325"))
				.body("site.id", equalTo("foo123"))
				.body("device.ip", equalTo("69.250.196.118"))
				.body("device.geo.country", equalTo("US"))
				.body("user.id", equalTo("9cb89r"))
				.time(lessThan(500L))
				.log();

		} catch (URISyntaxException e) {
			LOGGER.error("Test cannot be executed! Cannot get "+ positiveReqContentFile + "", e);
		} catch (IOException e) {
			LOGGER.error("Test cannot be executed! Cannot read "+ positiveReqContentFile + "", e);
		} catch(AssertionError e) {
			LOGGER.error("validSiteTest() failed!", e);
			e.printStackTrace();
		}
		
	}
	/**
	 * Tests '/api/enhance/site/' with invalid request 
	 */
	public void invalidSiteTest() {
		ClassLoader classLoader = getClass().getClassLoader();
		Path path = null;
		String content = null;
		try {
			path = Paths.get(classLoader.getResource(negativeReqContentFile).toURI());
			content = new String(Files.readAllBytes(path));
		
	
			given()
				.spec(spec)
				.body(content)
			.expect()
				.statusCode(500)
			.when()
				.post(URL)
			.then()
				.time(lessThan(500L))
				.log();
		} catch(AssertionError e) {
			LOGGER.error("invalidSiteTest() failed!", e);
			e.printStackTrace();
		}  catch (URISyntaxException e) {
			LOGGER.error("Test cannot be executed! Cannot get "+ positiveReqContentFile + "", e);
		} catch (IOException e) {
			LOGGER.error("Test cannot be executed! Cannot read "+ positiveReqContentFile + "", e);
		}
		
	}
}
