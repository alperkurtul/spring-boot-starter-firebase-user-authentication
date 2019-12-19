package com.github.alperkurtul.firebaseuserauthentication.service;

import com.github.alperkurtul.firebaseuserauthentication.bean.FirebaseRefreshTokenToIdTokenResponseBean;
import com.github.alperkurtul.firebaseuserauthentication.bean.FirebaseSignInSignUpResponseBean;
import com.github.alperkurtul.firebaseuserauthentication.constants.ApiUrlConstants;
import com.github.alperkurtul.firebaseuserauthentication.exception.HttpBadRequestException;
import com.github.alperkurtul.firebaseuserauthentication.exception.HttpNotFoundException;
import com.github.alperkurtul.firebaseuserauthentication.exception.HttpUnauthorizedException;
import com.github.alperkurtul.firebaseuserauthentication.utility.StringUtility;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.Properties;

@Component
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private String firebaseWebApiKey;
    private StringUtility stringUtility = null;

    public UserAuthenticationServiceImpl() {

        stringUtility = new StringUtility();

        Properties properties = new Properties();
        try {
            File file = ResourceUtils.getFile("classpath:firebase-web-api-key.txt");
            InputStream in = new FileInputStream(file);
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.firebaseWebApiKey = properties.getProperty("firebase-web-api-key");

    }

    @Override
    public FirebaseSignInSignUpResponseBean signInWithEmailAndPassword(String email, String password) {

        HttpEntity<String> request = createPostRequestBodyForEmailAndPassword(email, password);

        // This is the generated URI
        String url = stringUtility.findAndReplaceStringIntoString(ApiUrlConstants.FIREBASE_SIGNIN_EMAIL_AND_PASSWORD,
                                                                    ApiUrlConstants.FIREBASE_SIGNIN_EMAIL_AND_PASSWORD_TO_BE_CHANGED_PART,
                                                                    this.firebaseWebApiKey);

        ResponseEntity<FirebaseSignInSignUpResponseBean> responseEntity = (ResponseEntity<FirebaseSignInSignUpResponseBean>) doPostForEntity(url, request, FirebaseSignInSignUpResponseBean.class);

        return responseEntity.getBody();
    }

    @Override
    public void deleteUserAccount(String idToken) {

        HttpEntity<String> request = createPostRequestBodyForIdToken(idToken);

        // This is the generated URI
        String url = stringUtility.findAndReplaceStringIntoString(ApiUrlConstants.FIREBASE_DELETE_USER_ACCOUNT,
                ApiUrlConstants.FIREBASE_DELETE_USER_ACCOUNT_TO_BE_CHANGED_PART,
                this.firebaseWebApiKey);

        doPostForEntity(url, request, null);

    }

    @Override
    public FirebaseSignInSignUpResponseBean signUpWithEmailAndPassword(String email, String password) {

        HttpEntity<String> request = createPostRequestBodyForEmailAndPassword(email, password);

        // This is the generated URI
        String url = stringUtility.findAndReplaceStringIntoString(ApiUrlConstants.FIREBASE_SIGNUP_EMAIL_AND_PASSWORD,
                                                                    ApiUrlConstants.FIREBASE_SIGNUP_EMAIL_AND_PASSWORD_TO_BE_CHANGED_PART,
                                                                    this.firebaseWebApiKey);

        ResponseEntity<FirebaseSignInSignUpResponseBean> responseEntity = (ResponseEntity<FirebaseSignInSignUpResponseBean>) doPostForEntity(url, request, FirebaseSignInSignUpResponseBean.class);

        return responseEntity.getBody();
    }

    @Override
    public FirebaseRefreshTokenToIdTokenResponseBean exchangeRefreshTokenToIdToken(String refreshToken) {

        HttpEntity<MultiValueMap<String, String>> request = createPostRequestBodyForRefreshTokenToIdToken(refreshToken);

        // This is the generated URI
        String url = stringUtility.findAndReplaceStringIntoString(ApiUrlConstants.FIREBASE_EXCHANGE_REFRESH_TOKEN_TO_ID_TOKEN,
                                                                    ApiUrlConstants.FIREBASE_EXCHANGE_REFRESH_TOKEN_TO_ID_TOKEN_TO_BE_CHANGED_PART,
                                                                    this.firebaseWebApiKey);

        ResponseEntity<FirebaseRefreshTokenToIdTokenResponseBean> responseEntity = (ResponseEntity<FirebaseRefreshTokenToIdTokenResponseBean>) doPostForEntity(url, request, FirebaseRefreshTokenToIdTokenResponseBean.class);

        return responseEntity.getBody();
    }

    private ResponseEntity<?> doPostForEntity(String url, Object request, Class<?> responseType) {

        ResponseEntity<?> responseEntity = null;
        try {
            responseEntity = new RestTemplate().postForEntity(url, request, responseType);
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new HttpBadRequestException(e.getResponseBodyAsString());
            } else if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new HttpNotFoundException(e.getResponseBodyAsString());
            }else if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new HttpUnauthorizedException(e.getResponseBodyAsString());
            } else {
                throw new RuntimeException();
            }
        }

        return responseEntity;

    }

    private HttpEntity<String> createPostRequestBodyForEmailAndPassword(String email, String password) {

        JSONObject requestBodyJsonObject = new JSONObject();
        requestBodyJsonObject.put("email", email);
        requestBodyJsonObject.put("password", password);
        requestBodyJsonObject.put("returnSecureToken", "true");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestBody = new HttpEntity<String>(requestBodyJsonObject.toString(), httpHeaders);

        return requestBody;

    }

    private HttpEntity<String> createPostRequestBodyForIdToken(String idToken) {

        JSONObject requestBodyJsonObject = new JSONObject();
        requestBodyJsonObject.put("idToken", idToken);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestBody = new HttpEntity<String>(requestBodyJsonObject.toString(), httpHeaders);

        return requestBody;

    }

    private HttpEntity<MultiValueMap<String, String>> createPostRequestBodyForRefreshTokenToIdToken(String refreshToken) {

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("grant_type", "refresh_token");
        map.add("refresh_token", refreshToken);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestBody = new HttpEntity<MultiValueMap<String, String>>(map, httpHeaders);

        return requestBody;

    }

}
