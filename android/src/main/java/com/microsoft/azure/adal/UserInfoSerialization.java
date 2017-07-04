package com.microsoft.azure.adal;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.aad.adal.TokenCacheItem;
import com.microsoft.aad.adal.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class that responsible for simple serialization of ADAL primitives
 */
class UserInfoSerialization {

    public static WritableMap userInfoToWritableMap(UserInfo info) throws Exception {

        WritableMap userInfo = Arguments.createMap();

        if (info == null) {
            return userInfo;
        }

        userInfo.putString("displayableId", info.getDisplayableId());
        userInfo.putString("familyName", info.getFamilyName());
        userInfo.putString("givenName", info.getGivenName());
        userInfo.putString("identityProvider", info.getIdentityProvider());
        userInfo.putString("passwordChangeUrl", String.format("%s", info.getPasswordChangeUrl()));
        userInfo.putString("passwordExpiresOn", String.format("%s", info.getPasswordExpiresOn()));

        userInfo.putString("uniqueId", info.getUserId());
        userInfo.putString("userId", info.getUserId());

        return userInfo;
    }

    /**
     *
     * @param authenticationResult
     * @return
     * @throws Exception
     */
    public static WritableMap authenticationResultToWritableMap(AuthenticationResult authenticationResult) throws Exception {
        WritableMap authResult = Arguments.createMap();

        authResult.putString("accessToken", authenticationResult.getAccessToken());
        authResult.putString("accessTokenType", authenticationResult.getAccessTokenType());
        authResult.putString("expiresOn", String.format("%s", authenticationResult.getExpiresOn()));
        authResult.putString("idToken", authenticationResult.getIdToken());
        authResult.putBoolean("isMultipleResourceRefreshToken", authenticationResult.getIsMultiResourceRefreshToken());
        authResult.putString("statusCode", String.format("%s", authenticationResult.getStatus()));
        authResult.putString("tenantId", authenticationResult.getTenantId());

        WritableMap userInfo = null;
        try {
            userInfo = userInfoToWritableMap(authenticationResult.getUserInfo());
        } catch (Exception ignored) {}

        authResult.putMap("userInfo", userInfo);

        return authResult;
    }

}
