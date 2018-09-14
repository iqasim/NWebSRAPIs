package com.icreon.customer;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.icreon.constants.EndPoints;
import com.icreon.constants.Headers;
import com.icreon.constants.Path;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class kidTest {
	
	@BeforeClass
	public void setUp(){
		RestAssured.baseURI = Path.BASE_URI;
		RestAssured.basePath = Path.BASE_PATH;
	}
	
	@Test
	public void TC_001_SavingANewKid(){
		Map<String, Object> bodyParameters = new HashMap<String, Object>();
		bodyParameters.put("kidId", 0);
		bodyParameters.put("firstName", "Test Kid 1");
		bodyParameters.put("lastName", "Test Kid 1");
		bodyParameters.put("gender", 1);
		bodyParameters.put("dob", "2001-11-21 12:10:05.500");
		bodyParameters.put("affiliationId", 3);
		bodyParameters.put("ruleId", 26);
		bodyParameters.put("volunteerWaiverID", 28);
		bodyParameters.put("eventClassWaiverID", 33);
		bodyParameters.put("codeOfConductID", 24);
		bodyParameters.put("noMarathonVolunteer", 1);
		bodyParameters.put("oldAffiliateID", 0);
		
		String expectedResponseMessage = "Record already exist";
		
		Response response = 
		given()
			.header(Headers.AUTHORIZATION, "Bearer "+Headers.AUTH_TOKEN_VALUE)
			.header(Headers.X_FRED_AUTH, Headers.X_FRED_AUTH_VALUE)
			.header(Headers.DEVICE_TYPE, Headers.DEVICE_TYPE_VALUE)
			.header(Headers.CUSTOMER_ID, Headers.CUSTOMER_ID_VALUE)
			.header(Headers.CONTENT_TYPE, Headers.CONTENT_TYPE_VALUE)
			.body(bodyParameters)
		.when()
			.post(EndPoints.KID)
		.then()
		.extract()
		.response();
		
		String res = response.asString();
		JsonPath jpath = new JsonPath(res);
		String actualResponseMessage = jpath.get("responseMessage");
		assertEquals(expectedResponseMessage, actualResponseMessage);
	}

}
