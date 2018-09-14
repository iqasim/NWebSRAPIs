package com.icreon.constants;

import com.icreon.account.firstLoginTest;
import com.icreon.common.RestUtilities;

public class Headers {
	
	public static String AUTHORIZATION = "authorization";
	public static String X_FRED_AUTH = "x_fred_auth";
	public static String DEVICE_TYPE = "device_type";
	public static String CUSTOMER_ID = "customerID";
	public static String CONTENT_TYPE = "Content-Type";
	public static final String AUTHORIZATION_VALUE = "Basic ZW1haWwxMzcxNTUxMEBlbWFpbC5jb206ZjFlMzRhM2EzZjE0MDQwNjQ1YTg1MjkzZWZiMTZiMjgyOTEwNDBhYTRlMjMxMThhYTY0NmQ2ZTc5NDk4NmQzZQ==";
	public static final String X_FRED_AUTH_VALUE = "Q0YzU1MTE2YjMwNDQzZjZhMWQ3NWU5YTJmNTAyYTNhYWM3OWVkODk3MTU2YTE4OGM0NWUzOWY";
	public static final int DEVICE_TYPE_VALUE= 1;
	public static int CUSTOMER_ID_VALUE = RestUtilities.getcustID(); 
	public static String AUTH_TOKEN_VALUE = RestUtilities.getAuthToken();
	public static int WRONG_CUSTOMER_ID_VALUE = 1236869;
	public static String CONTENT_TYPE_VALUE = "application/json";

}
