
package com.microsoft.azure.adal;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.microsoft.aad.adal.AuthenticationContext;
import com.microsoft.aad.adal.AuthenticationSettings;

public class RNAzureAdalModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private String clientId;
    private String redirectUrl;
    private AuthenticationContext authContext;

    public RNAzureAdalModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;

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

    @Override
    public String getName() {
        return "RNAzureAdal";
    }

    /**
     * @param authority         Authority url to send code and token requests
     * @param validateAuthority validate authority before sending token request, ff you're directly talking to ADFS server, you should set to false.
     * @param clientId
     * @param redirectUrl
     * @param userBroker        True to use broker
     */

    @ReactMethod
    public void configure(String authority, Boolean validateAuthority, String clientId, String redirectUrl, Boolean userBroker) {
        AuthenticationSettings.INSTANCE.setUseBroker(userBroker);
        AuthenticationContext context = new AuthenticationContext(this.reactContext.getApplicationContext(), authority, validateAuthority);
        this.authContext = context;
        this.clientId = clientId;
        this.redirectUrl = redirectUrl;
    }

    @ReactMethod
    public void acquireTokenAsync(
            final String resourceUrl,
            final String loginHint,
            final String extraQueryParameters,
            final Promise promise
    ) {
        if (authContext != null) {
            final Activity activity = getCurrentActivity();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    authContext.acquireToken(activity, resourceUrl, clientId,
                            redirectUrl, loginHint, Constants.SHOW_PROMPT_AUTO,
                            extraQueryParameters, new RNDefaultAuthenticationCallback(promise));
                }
            });
        } else {
            // TODO -- pass error message
        }
    }

    @ReactMethod
    public void acquireTokenSilentAsync(
            final String resourceUrl,
            final String loginHint,
            final String extraQueryParameters,
            final Promise promise
    ) {
        if (authContext != null) {
            final Activity activity = getCurrentActivity();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    authContext.acquireToken(activity, resourceUrl, clientId,
                            redirectUrl, loginHint, Constants.SHOW_PROMPT_AUTO,
                            extraQueryParameters, new RNDefaultAuthenticationCallback(promise));
                }
            });
        } else {
            // TODO -- pass error message
        }
    }

    /**
     * @param clientId
     */
    @ReactMethod
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    // private methods
}