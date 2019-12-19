package com.github.alperkurtul.firebaseuserauthentication;

import com.github.alperkurtul.firebaseuserauthentication.bean.FirebaseRefreshTokenToIdTokenResponseBean;
import com.github.alperkurtul.firebaseuserauthentication.bean.FirebaseSignInSignUpResponseBean;
import com.github.alperkurtul.firebaseuserauthentication.configuration.UserAuthenticationConfiguration;
import com.github.alperkurtul.firebaseuserauthentication.exception.HttpBadRequestException;
import com.github.alperkurtul.firebaseuserauthentication.service.UserAuthenticationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@SpringBootTest(classes = UserAuthenticationConfiguration.class)
@RunWith(SpringRunner.class)
@Import(UserAuthenticationImplTest.UserAuthenticationImplTestConfiguration.class)
class UserAuthenticationImplTest {

    private String testEmail = "junittest999@test.com";
    private String testPassword = "junittest999";

    @Autowired
    UserAuthenticationServiceImpl userAuthenticationServiceImpl;

    @TestConfiguration
    public static class UserAuthenticationImplTestConfiguration {

        @Bean
        public UserAuthenticationServiceImpl userAuthenticationServiceImpl() {
            return new UserAuthenticationServiceImpl();
        }

    }

    @Test
    public void signUpSignInWithEmailAndPassword() {

        FirebaseSignInSignUpResponseBean firebaseSignUpUser = userAuthenticationServiceImpl.signUpWithEmailAndPassword(this.testEmail,this.testPassword);
        assertNotNull(firebaseSignUpUser);
        assertNotNull(firebaseSignUpUser.getIdToken());
        assertNotNull(firebaseSignUpUser.getRefreshToken());
        assertNotEquals(firebaseSignUpUser.getIdToken(), "");
        assertNotEquals(firebaseSignUpUser.getRefreshToken(), "");
        assertThat(firebaseSignUpUser.getEmail(), is(this.testEmail));

        FirebaseSignInSignUpResponseBean firebaseSignInUser = userAuthenticationServiceImpl.signInWithEmailAndPassword(firebaseSignUpUser.getEmail(), this.testPassword);
        assertNotNull(firebaseSignInUser);
        assertNotNull(firebaseSignInUser.getIdToken());
        assertNotNull(firebaseSignInUser.getRefreshToken());
        assertNotEquals(firebaseSignInUser.getIdToken(), "");
        assertNotEquals(firebaseSignInUser.getRefreshToken(), "");
        assertThat(firebaseSignInUser.getLocalId(), is(firebaseSignUpUser.getLocalId()));  // UID
        assertNotEquals(firebaseSignInUser.getRefreshToken(), firebaseSignUpUser.getRefreshToken());

        FirebaseRefreshTokenToIdTokenResponseBean firebaseRefreshTokenToIdTokenResponseBean1 = userAuthenticationServiceImpl.exchangeRefreshTokenToIdToken(firebaseSignUpUser.getRefreshToken()); // Every refreshToken is valid

        FirebaseRefreshTokenToIdTokenResponseBean firebaseRefreshTokenToIdTokenResponseBean2 = userAuthenticationServiceImpl.exchangeRefreshTokenToIdToken(firebaseSignInUser.getRefreshToken()); // Every refreshToken is valid

        try {
            userAuthenticationServiceImpl.deleteUserAccount(firebaseRefreshTokenToIdTokenResponseBean1.getId_token()); // Every idToken is valid
        } catch (AssertionError e) {
            System.out.println("hata!");
        }

        try {
            FirebaseSignInSignUpResponseBean deletedUser = userAuthenticationServiceImpl.signInWithEmailAndPassword(this.testEmail, this.testPassword);
        } catch (HttpBadRequestException e) {
            assertNotEquals(e.getMessage().indexOf("EMAIL_NOT_FOUND"), -1);
        }

    }

}
