# Argus- Onboarding Simplified

Argus Android makes it hassle free of managing all onboarding related tasks such as Signup/Login/Forgot Password and Social Logins.

## Gradle
`compile 'com.moldedbits.argus:argus:0.1.0'`

## Maven
    <dependency>
        <groupId>com.moldedbits.argus</groupId>
        <artifactId>argus</artifactId>
        <version>0.1.0</version>
        <type>pom</type>
    </dependency>

## Features
#### 1. Built in Login/Signup Providers
Argus comes with built in social login providers as well as an `EmailLoginProvider` which can be easily 
 integrated with your custom APIs. Adding different providers is as simple as this
 
 ```Java
  List<BaseProvider> loginProviders = new ArrayList<>();
  loginProviders.add(new GoogleOnBoardingProvider());
  loginProviders.add(new FacebookOnBoardingProvider())
  ```
  
  and then supply these providers to Argus object
   
```Java
   new Argus.Builder().loginProviders(loginProviders)
```
   
#### 2. Custom Layouts for Signup and Login
If you don't like Argus default UI you can provide your own layout and Argus will take care of rest

```Java
new Argus.Builder().signupLayout(R.layout.custom_signup_layout)
```

#### 3. Adaptive Look and Feel
Argus is built keeping Material design in mind it automatically adapts colors defined in your `styles.xml`


#### 4. Customizable Look and Feel
Argus can be customized as per your needs using `ArgusTheme`

```Java
ArgusTheme argusTheme 
    = new ArgusTheme.Builder()
    .buttonColor(R.color.com_facebook_blue)
    .logo(R.drawable.app_logo)
    .build();
```

## Usage
Argus exposes `ArgusActivity` which can be set as launcher activity in `AndroidManifest` or started using an Intent.
Currently it redirects to Login screen by default but in future we would let developer choose where he want to start.

A basic configuration of Argus looks like this
```Java
// initialize Argus
        ArrayList<BaseProvider> loginProviders = new ArrayList<>();
        ArrayList<BaseProvider> signupProviders = new ArrayList<>();
        PlaygroundSignupProvider playgroundSignupProvider = new PlaygroundSignupProvider(false);
        playgroundSignupProvider.getValidationEngine()
                .addPasswordValidation(new LengthValidation(4, 8, getString(R.string.password_length)));

        loginProviders.add(new PlaygroundLoginProvider());
        signupProviders.add(playgroundSignupProvider);

        ArgusTheme.Builder themeBuilder = new ArgusTheme.Builder();
        themeBuilder.logo(R.drawable.logo_3x);

        new Argus.Builder()
                .argusStorage(new DefaultArgusStorage(getApplicationContext()))
                .nextScreenProvider(new SimpleNextScreenProvider(ProjectListActivity.class))
                .signupProviders(signupProviders)
                .loginProviders(loginProviders)
                .theme(themeBuilder.build())
                .build();
```

Please feel free to contribute or report issues.

License
=======

    Copyright 2013 moldedbits

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
