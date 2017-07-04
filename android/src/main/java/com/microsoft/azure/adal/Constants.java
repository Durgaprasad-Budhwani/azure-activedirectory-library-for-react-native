package com.microsoft.azure.adal;

import com.microsoft.aad.adal.PromptBehavior;

public class Constants {

    public static final PromptBehavior SHOW_PROMPT_AUTO = PromptBehavior.Auto;
    public static final int GET_ACCOUNTS_PERMISSION_REQ_CODE = 0;
    public static final String PERMISSION_DENIED_ERROR = "Permissions denied";
    public static final String SECRET_KEY = "com.microsoft.aad.adal";
    public static final int MODE_PRIVATE = 0;

    /**
     * UTF-8 encoding
     */
    public static final String UTF8_ENCODING = "UTF-8";

    /**
     * SECRET_FACTORY_INSTANCE_ID
     */
    public static final String SECRET_FACTORY_INSTANCE_ID = "PBEWithSHA256And256BitAES-CBC-BC";

    /**
     * ALGORITHM_TYPE
     */
    public static final String ALGORITHM_TYPE = "AES";

    public static final String SALT = "abcdedfdfd";

}
