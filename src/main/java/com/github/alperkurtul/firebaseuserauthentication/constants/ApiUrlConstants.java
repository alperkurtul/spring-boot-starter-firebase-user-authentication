package com.github.alperkurtul.firebaseuserauthentication.constants;

public class ApiUrlConstants {

    public final static String FIREBASE_SIGNIN_EMAIL_AND_PASSWORD = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=[API_KEY]";
    public final static String FIREBASE_SIGNIN_EMAIL_AND_PASSWORD_TO_BE_CHANGED_PART = "[API_KEY]";

    public final static String FIREBASE_SIGNUP_EMAIL_AND_PASSWORD = "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=[API_KEY]";
    public final static String FIREBASE_SIGNUP_EMAIL_AND_PASSWORD_TO_BE_CHANGED_PART = "[API_KEY]";

    public final static String FIREBASE_EXCHANGE_REFRESH_TOKEN_TO_ID_TOKEN = "https://securetoken.googleapis.com/v1/token?key=[API_KEY]";
    public final static String FIREBASE_EXCHANGE_REFRESH_TOKEN_TO_ID_TOKEN_TO_BE_CHANGED_PART = "[API_KEY]";

    public final static String FIREBASE_DELETE_USER_ACCOUNT = "https://identitytoolkit.googleapis.com/v1/accounts:delete?key=[API_KEY]";
    public final static String FIREBASE_DELETE_USER_ACCOUNT_TO_BE_CHANGED_PART = "[API_KEY]";

}
