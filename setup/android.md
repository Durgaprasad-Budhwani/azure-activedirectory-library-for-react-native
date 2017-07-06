# Android Setup: react-native-azure-adal

## Assumptions

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
...
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

#### Step 1
Setup JAVA_HOME path

```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_45.jdk/Contents/Home
```

#### Step 2 - Script Way
Run script brokerRedirectPrint.sh (brokerRedirectPrint.sh will be available at root folder of plugin react-native-azure-adal)

```bash
chmod a+x brokerRedirectPrint.sh
sh brokerRedirectPrint.sh -r -c "com.example" -k "/Users/durgaprasad/code/azure/azure-activedirectory-library-for-react-native/example/android/app/azure-example.keystore" -a "example-alias" -p "example-password" 

```

It will generate redirectUri for ADAL broker based authentication

Output

```
msauth://com.example/9maof%2B5SYhc8dc%2Fni%2BFefl6unpw%3D
```

#### Manual Way

Run below command to get certificate key

```bash
keytool -storepass "example-password" -exportcert -alias "example-alias" -keystore "/Users/durgaprasad/code/azure/azure-activedirectory-library-for-react-native/example/android/app/azure-example.keystore"  | openssl sha1 -binary |  openssl base64 
```

It will give you certificate value

```bash
8BhOF7pgEpduSQKBKziiWZDhIVA=
```

Url encode this value below command  

```bash
 ruby -r cgi -e 'puts CGI.escape("Your certificate value")'
 # e.g ruby -r cgi -e 'puts CGI.escape("8BhOF7pgEpduSQKBKziiWZDhIVA=")'
 # RESULT 8BhOF7pgEpduSQKBKziiWZDhIVA%3D
```

Create url 

```bash
msauth://package_name/encoded_certificate_value

msauth://com.example/9maof%2B5SYhc8dc%2Fni%2BFefl6unpw%3D
```


##TroubleShooting

Receiving issues After an update? Please:

- Ensure this module is up to date
- Clean your gradle build cd android && ./gradlew clean && cd ..
- Ensure your AndroidManifest.xml is up to date
- If you are still receiving issues after all this then open an issue.

Thanks