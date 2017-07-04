/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies, Inc.
 * All Rights Reserved
 * See License in the project root for license information.
 ******************************************************************************/

// Modifications by Durgaprasad Budhwani to work with React Native instead of Cordova

package com.microsoft.azure.adal;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableMap;
import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationResult;

/**
 * Class that provides implementation for passing AuthenticationResult from acquireToken* methods
 * to React Native JS code
 */
class DefaultAuthenticationCallback implements AuthenticationCallback<AuthenticationResult> {

    /**
     * Private field that stores cordova callback context which is used to send results back to JS
     */
    private final Promise callbackPromise;

    /**
     * Default constructor
     * @param callbackPromise Callback Promise which is used to send results back to JS
     */
    DefaultAuthenticationCallback(Promise callbackPromise){
        this.callbackPromise = callbackPromise;
    }

    /**
     * Success callback that serializes AuthenticationResult instance and passes it to React Native
     * @param authResult AuthenticationResult instance
     */
    @Override
    public void onSuccess(AuthenticationResult authResult) {

        WritableMap result;
        try {
            result = UserInfoSerialization.authenticationResultToWritableMap(authResult);
            this.callbackPromise.resolve(result);
        } catch (Exception e) {
            this.callbackPromise.reject(new Exception("Failed to serialize Authentication result"));
        }
    }

    /**
     * Error callback that passes error to React Native
     * @param authException AuthenticationException
     */
    @Override
    public void onError(Exception authException) {
        this.callbackPromise.reject(authException);
    }
}
