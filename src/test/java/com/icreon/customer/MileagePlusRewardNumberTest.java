package com.icreon.customer;

import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.icreon.constants.EndPoints;
import com.icreon.constants.Headers;
import com.icreon.constants.Path;

import io.restassured.RestAssured;

public class MileagePlusRewardNumberTest {
	
	@BeforeClass
	public void setUp(){
		RestAssured.baseURI = Path.BASE_URI;
		RestAssured.basePath = Path.BASE_PATH;
	}
	
	@Test
	public void TC_001_MileageNumberAlreadyExist(){
		
		given()
			.header(Headers.AUTHORIZATION, "Bearer "+Headers.AUTH_TOKEN_VALUE)
			.header(Headers.X_FRED_AUTH, Headers.X_FRED_AUTH_VALUE)
			.header(Headers.DEVICE_TYPE, Headers.DEVICE_TYPE_VALUE)
			.header(Headers.CUSTOMER_ID, Headers.CUSTOMER_ID_VALUE)
			.header(Headers.CONTENT_TYPE, Headers.CONTENT_TYPE_VALUE)
			.queryParam("MileagePlusRewardNumber", "EN884044")
		.when()
			.post(EndPoints.MILEAGE_PLUS_REWARD_NUMBER)
		.then()
		.log()
		.all()
		.statusCode(200);
	}
	
	@Test
	public void TC_002_MileageNumberNotValid(){
		
		given()
			.header(Headers.AUTHORIZATION, "Bearer "+Headers.AUTH_TOKEN_VALUE)
			.header(Headers.X_FRED_AUTH, Headers.X_FRED_AUTH_VALUE)
			.header(Headers.DEVICE_TYPE, Headers.DEVICE_TYPE_VALUE)
			.header(Headers.CUSTOMER_ID, Headers.CUSTOMER_ID_VALUE)
			.header(Headers.CONTENT_TYPE, Headers.CONTENT_TYPE_VALUE)
			.queryParam("MileagePlusRewardNumber", "EN884049")
		.when()
			.post(EndPoints.MILEAGE_PLUS_REWARD_NUMBER)
		.then()
		.log()
		.all()
		.statusCode(200);
	}

}
