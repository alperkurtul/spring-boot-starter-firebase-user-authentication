# `Firebase User Management and Authentication` in Spring Boot
This project gives you the ability to use `Firebase User Management and Authentication` methods. To achieve this, you just have to add the dependency and simply use the methods in your Spring Boot application. These are the list of methods you can use in this release(1.0.0):

- signUpWithEmailAndPassword
- signInWithEmailAndPassword
- deleteUserAccount
- exchangeRefreshTokenToIdToken

## How to Apply

### Configuration

Put a `firebase-web-api-key.txt` file in the root of classpath and specify `firebase-web-api-key : ` property in it.
```properties
firebase-web-api-key : [your-firebase-project-web-api-key]
```

In a `@Configuration` class in your application, create a `@Bean` for `UserAuthenticationServiceImpl` class.
```java
@Configuration
public class DemoConfig {

    @Bean
    public UserAuthenticationServiceImpl userAuthenticationServiceImpl() {
        return new UserAuthenticationServiceImpl();
    }
    
}
```

### Dependencies

Primarily, you have to add `spring-boot-starter-web` dependency in your Spring Boot application.
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

Then, you have to also add this dependency in your `pom.xml`.
```xml
<dependency>
    <groupId>com.github.alperkurtul</groupId>
    <artifactId>spring-boot-starter-firebase-user-authentication</artifactId>
    <version>1.0.0.RELEASE</version>
</dependency>
```

### and How to Use

Just simply use methods.

```java
@Autowired
private UserAuthenticationServiceImpl userAuthenticationServiceImpl;
```

```java
FirebaseSignInSignUpResponseBean firebaseSignInSignUpResponseBean = userAuthenticationServiceImpl.signInWithEmailAndPassword("test7@test.com", "test07");
```

### Demo

Here is a demo that I made for you. <a href="https://github.com/alperkurtul/spring-boot-starter-firebase-realtime-database-demo">`Demo`</a>

## Next

I hope, I will add new features in the next. Don't be shy to send your advice to me.
Take care...


