package com.icreon.common;

import static org.hamcrest.Matchers.lessThan;
import static io.restassured.RestAssured.given;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.icreon.constants.Auth;
import com.icreon.constants.EndPoints;
import com.icreon.constants.Headers;
import com.icreon.constants.Path;

import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RestUtilities {
	public static String ENDPOINT;
	public static RequestSpecBuilder REQUEST_BUILDER;
	public static RequestSpecification REQUEST_SPEC;
	public static ResponseSpecBuilder RESPONSE_BUILDER;
	public static ResponseSpecification RESPONSE_SPEC;
	
	public static void setEndpoint(String epoint){
		ENDPOINT = epoint;
	}
	
	public static RequestSpecification getRequestSpecification(){
		REQUEST_BUILDER = new RequestSpecBuilder();
		REQUEST_BUILDER.setBaseUri(Path.BASE_URI);
		REQUEST_BUILDER.setBasePath(Path.BASE_PATH);
		REQUEST_SPEC = REQUEST_BUILDER.build();
		return REQUEST_SPEC;
	}
	
	public static ResponseSpecification getResponseSpecification(){
		RESPONSE_BUILDER = new ResponseSpecBuilder();
		/*RESPONSE_BUILDER.expectStatusCode(200);
		RESPONSE_BUILDER.expectResponseTime(lessThan(3L), TimeUnit.SECONDS);*/
		RESPONSE_SPEC = RESPONSE_BUILDER.build();
		return RESPONSE_SPEC;
	}
	
	public static RequestSpecification createQueryParam(RequestSpecification rspec,
			String param, String value){
		return rspec.queryParam(param, value);
	}
	
	public static RequestSpecification createQueryParam(RequestSpecification rspec,
			Map<String, String> queryMap){
		return rspec.queryParams(queryMap);
	}
	
	public static RequestSpecification createPathParam(RequestSpecification rspec,
			String param, String value){
		return rspec.pathParam(param, value);
	}
	
	public static Response getResponse(){
		return given().get(ENDPOINT);
	}
	
	public static Response getResponse(RequestSpecification reqspec, String type){
		REQUEST_SPEC.spec(reqspec);
		Response response = null;
		if(type.equalsIgnoreCase("get")){
			response = given().spec(REQUEST_SPEC).get(ENDPOINT); 
		} else if (type.equalsIgnoreCase("post")){
			response = given().spec(REQUEST_SPEC).post(ENDPOINT); 
		} else if(type.equalsIgnoreCase("put")){
			response = given().spec(REQUEST_SPEC).put(ENDPOINT); 
		} else if (type.equalsIgnoreCase("delete")){
			response = given().spec(REQUEST_SPEC).delete(ENDPOINT); 
		} else {
			System.out.println(type + " is not supported");
		}
		response.then().log().all();
		response.then().spec(RESPONSE_SPEC);
		return response;
	}
	
	public static JsonPath getJsonPath (Response res){
		String path = res.asString();
		return new JsonPath(path);
	}
	
	public static XmlPath getXmlPath (Response res){
		String path = res.asString();
		return new XmlPath(path);
	}

	public static void resetBasePath(){
		RestAssured.basePath = null;
	}
	
	public static void setContentType(ContentType type){
		given().contentType(type);
	}
	
	public static RequestSpecification createHeaders(RequestSpecification rspec,
			String param, String value){
		return rspec.header(param, value);
	}
	
	
	public static String getAuthToken(){
		Response response =
				given()
					.header(Headers.AUTHORIZATION, Headers.AUTHORIZATION_VALUE)
					.header(Headers.X_FRED_AUTH, Headers.X_FRED_AUTH_VALUE)
					.header(Headers.DEVICE_TYPE, Headers.DEVICE_TYPE_VALUE)
				.when()
					.get(EndPoints.FRED_LOGIN)
				.then()
					.extract()
					.response();
				String res = response.asString();
				JsonPath path = new JsonPath(res);
				String AUTH_TOKEN = path.get("responseData.authToken");
				//System.out.println("Auth Token inside getAuthToken "+AUTH_TOKEN);
				return AUTH_TOKEN;
	}

	public static int getcustID(){
		Response response =
				given()
					.header(Headers.AUTHORIZATION, Headers.AUTHORIZATION_VALUE)
					.header(Headers.X_FRED_AUTH, Headers.X_FRED_AUTH_VALUE)
					.header(Headers.DEVICE_TYPE, Headers.DEVICE_TYPE_VALUE)
				.when()
					.get(EndPoints.FRED_LOGIN)
				.then()
					.extract()
					.response();
				String res = response.asString();
				JsonPath path = new JsonPath(res);
				int CUSTOMER_ID = path.get("responseData.customerId");
				//System.out.println("Customer Id inside getcustID "+CUSTOMER_ID);
				return CUSTOMER_ID;
	}
	
	public static int getProfileID(){
		Response response =
				given()
					.header(Headers.AUTHORIZATION, Headers.AUTH_TOKEN_VALUE)
					.header(Headers.X_FRED_AUTH, Headers.X_FRED_AUTH_VALUE)
					.header(Headers.DEVICE_TYPE, Headers.DEVICE_TYPE_VALUE)
					.header(Headers.CUSTOMER_ID, Headers.CUSTOMER_ID_VALUE)
				.when()
					.get(EndPoints.CUSTOMER_PROFILE_INFO+"/0")
				.then()
					.extract()
					.response();
				String res = response.asString();
				JsonPath path = new JsonPath(res);
				int PROFILE_ID = path.get("responseData.profileId");
				//System.out.println("Customer Id inside getcustID "+PROFILE_ID);
				return PROFILE_ID;
	}
	


}
