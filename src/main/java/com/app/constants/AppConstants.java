package com.app.constants;

//@Component
public class AppConstants {

	
	public static final String tokenSecret = "mysecret";

	public static final String startWith = "Bearer ";

	public static final Long validityInMilliseconds = 1000*60*6L;  // 6 minute
	
	public static final String tokenAuthorities = "USER_ROLES";   // define key for user roles in token, roles can be accessed using this key later
	
	public static final String invalidToken = "Token Details Are Not Correct . Please Login Again to Continue !!";

	public static final String expiredToken = "Token Has Been Expired . Please Login Again to Continue !!";

	public static final String noHeader = "Header Is Not Present . Please Provide Valid Header !!";
	
	
}
