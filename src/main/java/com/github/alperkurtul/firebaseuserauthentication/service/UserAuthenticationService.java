package com.github.alperkurtul.firebaseuserauthentication.service;

import com.github.alperkurtul.firebaseuserauthentication.bean.FirebaseRefreshTokenToIdTokenResponseBean;
import com.github.alperkurtul.firebaseuserauthentication.bean.FirebaseSignInSignUpResponseBean;

public interface UserAuthenticationService {

    public FirebaseSignInSignUpResponseBean signUpWithEmailAndPassword(String email, String password);

    public FirebaseSignInSignUpResponseBean signInWithEmailAndPassword(String email, String password);

    public void deleteUserAccount(String idToken);

    public FirebaseRefreshTokenToIdTokenResponseBean exchangeRefreshTokenToIdToken(String refreshToken);

}
