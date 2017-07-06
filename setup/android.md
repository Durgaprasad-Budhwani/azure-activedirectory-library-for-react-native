#Android Setup: react-native-azure-adal

##Assumptions

You have a Azure AD Setup (TODO - markdown)

## Step 1

Install NPM module 

`$ npm install react-native-azure-adal --save`

OR 

`yarn add react-native-azure-adal`

## Step 2 - Linking

### Mostly automatic installation

	$ react-native link react-native-azure-adal

### Manual installation - Android


1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.microsoft.azure.adal.RNAzureAdalPackage;` to the imports at the top of the file
  - Add `new RNAzureAdalPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:

	```
	include ':react-native-azure-adal'
	project(':react-native-azure-adal').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-azure-adal/android')
	```
  	
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:

  	```
      compile project(':react-native-azure-adal')
  	```

## AndroidManifest.xml Changes
Update your project's AndroidManifest.xml file to include:

```xml
...
<!-- Add permissions -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

<application
    android:allowBackup="true"
    android:debuggable="true"
    android:icon="@drawable/ic_launcher"
    android:label="@string/app_name"
    android:theme="@style/AppTheme" >
    
	<!--add AuthenticationActivity-->
    <activity
        android:name="com.microsoft.aad.adal.AuthenticationActivity"
        android:label="@string/title_login_hello_app" >
    </activity>

<application/>
```

## Broker Based Authentication

### AndroidManifest.xml Changes

If target version is lower than 23, calling app has to have the following permissions declared in manifest(http://developer.android.com/reference/android/accounts/AccountManager.html):

- GET_ACCOUNTS
- USE_CREDENTIALS
- MANAGE_ACOUNTS

If target version is 23, **USE_CREDENTIALS** and **MANAGE_ACCOUNTS** are already deprecated. But GET_ACCOUNTS is under protection level "dangerous", calling app is responsible for requesting the run-time permisson.

Update your project's AndroidManifest.xml file to include below permissions:

```xml
<uses-permission android:name="android.permission.GET_ACCOUNTS" />
<uses-permission android:name="android.permission.USE_CREDENTIALS" />
<uses-permission android:name="android.permission.MANAGE_ACCOUNTS" />
```


### Broker Redirection Uri

Developer needs to register special redirectUri for broker usage. RedirectUri is in the format of **msauth://packagename/Base64UrlencodedSignature**. You can get your redirecturi for your app using the script **brokerRedirectPrint.ps1** on Windows or **brokerRedirectPrint.sh** on Linux or Mac. You can also use API call mContext.getBrokerRedirectUri. Signature is related to your signing certificates.

// TODO - put command for windows and ios

e.g.

```
msauth://com.example/9maof%2B5SYhc8dc%2Fni%2BFefl6unpw%3D
```

##TroubleShooting

Receiving issues After an update? Please:

- Ensure this module is up to date
- Clean your gradle build cd android && ./gradlew clean && cd ..
- Ensure your AndroidManifest.xml is up to date
- If you are still receiving issues after all this then open an issue.

Thanks