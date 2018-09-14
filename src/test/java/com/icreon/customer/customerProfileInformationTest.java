package com.icreon.customer;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.icreon.constants.EndPoints;
import com.icreon.constants.Headers;
import com.icreon.constants.Path;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class customerProfileInformationTest {
	
	RequestSpecification reqSpec;
	ResponseSpecification resSpec;
	
	@BeforeClass
	public void setUp(){
		//reqSpec = RestUtilities.getRequestSpecification();
		
		RestAssured.baseURI = Path.BASE_URI;
		RestAssured.basePath = Path.BASE_PATH;
	}
	
	@Test
	public void custProfileInfo(){
		//System.out.println("Authentication Token inside Customer Profile Info: "+ RestUtilities.getAuthToken());
		String expectedCity = "Scarborough";
		Response response =
		given()
		.header(Headers.AUTHORIZATION, "Bearer "+Headers.AUTH_TOKEN_VALUE)
		.header(Headers.X_FRED_AUTH, Headers.X_FRED_AUTH_VALUE)
		.header(Headers.DEVICE_TYPE, Headers.DEVICE_TYPE_VALUE)
		.header(Headers.CUSTOMER_ID, Headers.CUSTOMER_ID_VALUE)
		.when()
			.get(EndPoints.CUSTOMER_PROFILE_INFO)
		.then()
			.extract()
			.response();
		String res = response.asString();
		JsonPath jsonPath = new JsonPath(res);
		String actualCity = jsonPath.get("responseData.mailingAddress.city");
		Assert.assertEquals(expectedCity, actualCity);
	}
	
	@Test
	public void TC_001_Passing0InPath(){
		int expectedResponseCode = 200;
		Response response =
		given()
			.header(Headers.AUTHORIZATION, "Bearer "+Headers.AUTH_TOKEN_VALUE)
			.header(Headers.X_FRED_AUTH, Headers.X_FRED_AUTH_VALUE)
			.header(Headers.DEVICE_TYPE, Headers.DEVICE_TYPE_VALUE)
			.header(Headers.CUSTOMER_ID, Headers.CUSTOMER_ID_VALUE)
		.when()
			.get(EndPoints.CUSTOMER_CC_PROFILES+"/0")
		.then()
			.extract()
			.response();
		String res = response.asString();
		JsonPath path = new JsonPath(res);
		int actualResponseCode = path.getInt("responseCode");
		assertEquals(expectedResponseCode,actualResponseCode);
	}
	
	@Test
	public void TC_002_PassingProfileIDinPath(){
		int expectedResponseCode = 200;
		Response response =
				given()
					.header(Headers.AUTHORIZATION, "Bearer "+Headers.AUTH_TOKEN_VALUE)
					.header(Headers.X_FRED_AUTH, Headers.X_FRED_AUTH_VALUE)
					.header(Headers.DEVICE_TYPE, Headers.DEVICE_TYPE_VALUE)
					.header(Headers.CUSTOMER_ID, Headers.CUSTOMER_ID_VALUE)
				.when()
					.get(EndPoints.CUSTOMER_PROFILE_INFO_PROFILE_ID_IN_PATH)
				.then()
					.extract()
					.response();
				String res = response.asString();
		JsonPath jpath = new JsonPath(res);
		int actualResponseCode = jpath.get("responseCode");
		assertEquals(expectedResponseCode, actualResponseCode);
	}
	
	@Test
	public void TC_003_PassingWrongProfileIDinPath(){
		int expectedResponseCode = 404;
		Response response = given()
		.header(Headers.AUTHORIZATION, "Bearer "+Headers.AUTH_TOKEN_VALUE)
		.header(Headers.X_FRED_AUTH, Headers.X_FRED_AUTH_VALUE)
		.header(Headers.DEVICE_TYPE, Headers.DEVICE_TYPE_VALUE)
		.header(Headers.CUSTOMER_ID, Headers.CUSTOMER_ID_VALUE)
		.when()
			.get(EndPoints.CUSTOMER_PROFILE_INFO_WRONG_PROFILE_ID_IN_PATH)
		.then()
			.extract()
			.response();
		String res = response.asString();
		JsonPath jpath = new JsonPath(res);
		int actualResponseCode = jpath.get("responseCode");
		assertEquals(expectedResponseCode, actualResponseCode);	
	}
	
	@Test
	public void TC_004_requestWithoutAuthorization(){
		given()
			.header(Headers.X_FRED_AUTH, Headers.X_FRED_AUTH_VALUE)
			.header(Headers.DEVICE_TYPE, Headers.DEVICE_TYPE_VALUE)
			.header(Headers.CUSTOMER_ID, Headers.CUSTOMER_ID_VALUE)
		.when()
			.get(EndPoints.CUSTOMER_PROFILE_INFO_PROFILE_ID_IN_PATH)
		.then()
			.log()
			.body()
			.statusCode(401);
	}
	
	@Test
	public void TC_005_requestWithoutX_Fred_Auth(){
		given()
			.header(Headers.AUTHORIZATION, "Bearer "+Headers.AUTH_TOKEN_VALUE)
			.header(Headers.DEVICE_TYPE, Headers.DEVICE_TYPE_VALUE)
			.header(Headers.CUSTOMER_ID, Headers.CUSTOMER_ID_VALUE)
		.when()
			.get(EndPoints.CUSTOMER_PROFILE_INFO_PROFILE_ID_IN_PATH)
		.then()
			.log()
			.body()
			.statusCode(401);
	}
	
	@Test
	public void TC_006_requestWithoutCustomerId(){
		given()
			.header(Headers.AUTHORIZATION, "Bearer "+Headers.AUTH_TOKEN_VALUE)
			.header(Headers.X_FRED_AUTH, Headers.X_FRED_AUTH_VALUE)
			.header(Headers.DEVICE_TYPE, Headers.DEVICE_TYPE_VALUE)
		.when()
			.get(EndPoints.CUSTOMER_PROFILE_INFO_PROFILE_ID_IN_PATH)
		.then()
			.log()
			.body()
			.statusCode(401);
	}
	
	@Test
	public void TC_007_mismatchedCustomerId(){
		given()
			.header(Headers.AUTHORIZATION, "Bearer "+Headers.AUTH_TOKEN_VALUE)
			.header(Headers.X_FRED_AUTH, Headers.X_FRED_AUTH_VALUE)
			.header(Headers.DEVICE_TYPE, Headers.DEVICE_TYPE_VALUE)
			.header(Headers.CUSTOMER_ID, Headers.WRONG_CUSTOMER_ID_VALUE)
		.when()
			.get(EndPoints.CUSTOMER_PROFILE_INFO_PROFILE_ID_IN_PATH)
		.then()
			.log()
			.body()
			.statusCode(401);
	}
	
	
}
