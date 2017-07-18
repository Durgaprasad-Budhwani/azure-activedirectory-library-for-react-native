package com.microsoft.azure.adal;

import com.microsoft.aad.adal.PromptBehavior;

public class Constants {

    /**
     * SHOW_PROMPT_AUTO
     */
    public static final PromptBehavior SHOW_PROMPT_AUTO = PromptBehavior.Auto;

    /**
     * SHOW_PROMPT_ALWAYS
     */
    public static final PromptBehavior SHOW_PROMPT_ALWAYS = PromptBehavior.Always;

    /**
     * SECRET_KEY
     */
    public static final String SECRET_KEY = "com.microsoft.aad.adal";

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

    /**
     * SALT
     */
    public static final String SALT = "abcdedfdfd";

    /**
     * TAG
     */
    public static final String TAG = "AzureADALPlugin";

}
