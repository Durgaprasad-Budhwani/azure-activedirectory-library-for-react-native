
package com.microsoft.azure.adal;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

//import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.microsoft.aad.adal.AuthenticationContext;
import com.microsoft.aad.adal.AuthenticationSettings;

import java.util.Hashtable;

public class RNAzureAdalModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private Hashtable<String, Configuration> configurations = new Hashtable<>();
    private Configuration currentConfiguration;

    public RNAzureAdalModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        reactContext.addActivityEventListener(new RNAzureAdalActivityEventListener());
        // Android API < 18 does not support AndroidKeyStore so ADAL requires
        // some extra work to crete and pass secret key to ADAL.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            try {
                AdalUtils.SetupKey();
            } catch (Exception e) {
                Log.w("RNAzureAdalModule", "Unable to create secret key: " + e.getMessage());
            }
        }
    }

    /**
     *
     */
    private class RNAzureAdalActivityEventListener extends BaseActivityEventListener {
        @Override
        public void onActivityResult(Activity activity, final int requestCode, final int resultCode, final Intent intent) {
            super.onActivityResult(activity, requestCode, resultCode, intent);
            if (currentConfiguration != null && currentConfiguration.getAuthContext() != null) {
                currentConfiguration.getAuthContext().onActivityResult(requestCode, resultCode, intent);
            }
        }
    }


    @Override
    public String getName() {
        return "RNAzureAdal";
    }

    /**
     * @param authority         Authority url to send code and token requests
     * @param validateAuthority validate authority before sending token request, ff you're directly talking to ADFS server, you should set to false.
     * @param clientId          required client identifier.
     * @param redirectUrl       Optional. It will use packagename and provided suffix
     *                          for this.
     * @param useBroker        True to use broker
     */
    @ReactMethod
    public void configure(String authority,
                          Boolean validateAuthority,
                          String clientId,
                          String redirectUrl,
                          Boolean useBroker) {
        AuthenticationSettings.INSTANCE.setUseBroker(useBroker);
        Log.w("configure", authority);
        AuthenticationContext context = new AuthenticationContext(this.reactContext.getCurrentActivity(), authority, validateAuthority);
        Configuration configuration = new Configuration(authority, validateAuthority, clientId, redirectUrl, useBroker, context);
        configurations.put(authority, configuration);
        currentConfiguration = configuration;
    }


    /**
     * @param resourceUrl          required resource identifier.
     * @param loginHint            Optional if validateAuthority == null. It is used for cache and as a loginhint at
     *                             authentication.
     * @param extraQueryParameters Optional. added to authorization url
     * @param forceLogin           Optional. if true then it will show login windows
     * @param promise              Callback Promise which is used to send results back to JS
     */
    @ReactMethod
    public void acquireTokenAsync(
            final String resourceUrl,
            final String loginHint,
            final String extraQueryParameters,
            final Boolean forceLogin,
            final Promise promise
    ) {
        Log.w("configure", resourceUrl);
        if (currentConfiguration != null) {
            final Activity activity = getCurrentActivity();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AuthenticationContext authContext = currentConfiguration.getAuthContext();
                    String userId = AdalUtils.getUserIdFromCache(loginHint, authContext);
                    authContext.acquireToken(activity, resourceUrl, currentConfiguration.getClientId(),
                            currentConfiguration.getRedirectUrl(), userId, forceLogin ? Constants.SHOW_PROMPT_ALWAYS : Constants.SHOW_PROMPT_AUTO,
                            extraQueryParameters, new DefaultAuthenticationCallback(promise));
                }
            });
        } else {
            promise.reject(new Exception("Initialized Configure() method"));
        }
    }

    /**
     * @param resourceUrl          required resource identifier.
     * @param loginHint            Optional if validateAuthority == null. It is used for cache and as a loginhint at
     *                             authentication.
     * @param extraQueryParameters Optional. added to authorization url
     * @param forceLogin           Optional. if true then it will show login windows
     * @param promise              Callback Promise which is used to send results back to JS
     */
    @ReactMethod
    public void acquireInteractiveTokenAsync(
            final String resourceUrl,
            final String loginHint,
            final String extraQueryParameters,
            final Boolean forceLogin,
            final Promise promise
    ) {
        Log.w("configure", resourceUrl);
        if (currentConfiguration != null) {
            final Activity activity = getCurrentActivity();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AuthenticationContext authContext = currentConfiguration.getAuthContext();
                    String userId = AdalUtils.getUserIdFromCache(loginHint, authContext);
                    authContext.acquireToken(resourceUrl, currentConfiguration.getClientId(),
                            currentConfiguration.getRedirectUrl(), userId, forceLogin ? Constants.SHOW_PROMPT_ALWAYS : Constants.SHOW_PROMPT_AUTO,
                            extraQueryParameters, new DefaultAuthenticationCallback(promise));
                }
            });
        } else {
            promise.reject(new Exception("Initialized Configure() method"));
        }
    }

    /**
     * @param resourceUrl required resource identifier.
     * @param loginHint   Optional if validateAuthority == null. It is used for cache and as a loginhint at
     *                    authentication.
     * @param promise     Callback Promise which is used to send results back to JS
     */
    @ReactMethod
    public void acquireTokenSilentAsync(
            final String resourceUrl,
            final String loginHint,
            final Promise promise
    ) {
        if (currentConfiguration != null) {
            final Activity activity = getCurrentActivity();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AuthenticationContext authContext = currentConfiguration.getAuthContext();
                    String userId = AdalUtils.getUserIdFromCache(loginHint, authContext);
                    authContext.acquireTokenSilentAsync(resourceUrl, currentConfiguration.getClientId(),
                            userId, new DefaultAuthenticationCallback(promise));
                }
            });
        } else {
            promise.reject(new Exception("Initialized Configure() method"));
        }
    }

    /**
     * @param authority
     * @param promise
     */
    @ReactMethod
    public void swtichConfiguration(
            String authority,
            Promise promise
    ) {
        if (configurations.containsKey(authority)) {
            currentConfiguration = configurations.get(authority);
            promise.resolve(true);
        } else {
            promise.reject(new Exception("No configuration found"));
        }

    }

    /**
     *
     */
    @ReactMethod
    void clearCoockieAndCache() {
        // TODO -- need to make service call to clear cookie
        // https://login.microsoftonline.com/{0}/oauth2/logout?post_logout_redirect_uri={1}
        AdalUtils.clearCookies(this.reactContext);
        AuthenticationContext authContext = currentConfiguration.getAuthContext();
        authContext.getCache().removeAll();
    }
}