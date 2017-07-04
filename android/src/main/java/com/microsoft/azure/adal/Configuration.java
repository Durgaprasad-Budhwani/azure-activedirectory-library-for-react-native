package com.microsoft.azure.adal;

import com.microsoft.aad.adal.AuthenticationContext;

public class Configuration {

    private String authority;
    private Boolean validateAuthority;
    private String clientId;
    private String redirectUrl;
    private Boolean userBroker;
    private AuthenticationContext authContext;

    /**
     * @param authority         Authority url to send code and token requests
     * @param validateAuthority validate authority before sending token request, ff you're directly talking to ADFS server, you should set to false.
     * @param clientId          required client identifier.
     * @param redirectUrl       Optional. It will use packagename and provided suffix
     *                          for this.
     * @param useBroker        True to use broker
     * @param context           AuthenticationContext
     */
    public Configuration(String authority, Boolean validateAuthority, String clientId, String redirectUrl, Boolean useBroker, AuthenticationContext context) {
        this.authority = authority;
        this.validateAuthority = validateAuthority;
        this.clientId = clientId;
        this.redirectUrl = redirectUrl;
        this.userBroker = useBroker;
        this.authContext = context;
    }

    public String getAuthority() {
        return authority;
    }

    public Boolean getValidateAuthority() {
        return validateAuthority;
    }

    public String getClientId() {
        return clientId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public Boolean getUserBroker() {
        return userBroker;
    }

    public AuthenticationContext getAuthContext() {
        return authContext;
    }
}
