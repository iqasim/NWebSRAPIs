package com.icreon.account;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.icreon.common.RestUtilities;
import com.icreon.constants.EndPoints;
import com.icreon.constants.Path;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class firstLoginTest {
	
	/*RequestSpecification reqSpec;
	ResponseSpecification resSpec;*/
	
	public static String AUTH_TOKEN;
	
	@BeforeClass
	public void setUp(){
		RestAssured.baseURI = Path.BASE_URI;
		RestAssured.basePath = Path.BASE_PATH;
		/*	reqSpec = RestUtilities.getRequestSpecification();
		reqSpec.basePath(Path.STATUSES);
		resSpec = RestUtilities.getResponseSpecification(); */
	}
	
	@Test
	public void loginPortal(){
		Response response =
		given()
			.header("authorization", "Basic ZW1haWwxMzA5NjM0OUBlbWFpbC5jb206ZjFlMzRhM2EzZjE0MDQwNjQ1YTg1MjkzZWZiMTZiMjgyOTEwNDBhYTRlMjMxMThhYTY0NmQ2ZTc5NDk4NmQzZQ==")
			.header("x_fred_auth", "Q0YzU1MTE2YjMwNDQzZjZhMWQ3NWU5YTJmNTAyYTNhYWM3OWVkODk3MTU2YTE4OGM0NWUzOWY")
			.header("device_type", 1)
			//.spec(reqSpec)
		.when()
			.get(EndPoints.FRED_LOGIN)
		.then()
			.extract()
			.response();
			//.body("responseMessage", hasItem("Logged successfully"));
			//.body("responseMessage", hasItem("Logged successfully"))
			//.log().all();
		
		String res = response.asString();
		System.out.println(res);
		JsonPath path = new JsonPath(res);
		AUTH_TOKEN = path.get("responseData.authToken");
		System.out.println(AUTH_TOKEN);
	}
	
	@AfterClass
	public void tearDown(){
		System.out.println(AUTH_TOKEN);	
	}
	
}
