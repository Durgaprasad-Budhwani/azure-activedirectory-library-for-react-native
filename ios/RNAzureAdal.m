
#import "RNAzureAdal.h"
#import "Configuration.h"
#import "AdalUtils.h"
#import "UserInfoSerialization.h"
#import <ADAL/ADAL.h>

@implementation RNAzureAdal

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()

static NSMutableDictionary *configurations = nil;
static Configuration *currentConfiguration = nil;

/**
 * @param authority         Authority url to send code and token requests
 * @param validateAuthority validate authority before sending token request, ff you're directly talking to ADFS server, you should set to false.
 * @param clientId          required client identifier.
 * @param redirectUrl       Optional. It will use packagename and provided suffix
 *                          for this.
 * @param useBroker        True to use broker
 */
RCT_REMAP_METHOD(configure,
                 authority:(NSString *)authority
                 validateAuthority:(BOOL)validateAuthority
                 clientId:(NSString *)clientId
                 redirectUrl:(NSString *)redirectUrl
                 useBroker:(BOOL)useBroker
                 resolver:(RCTPromiseResolveBlock)resolve
                 rejecter:(RCTPromiseRejectBlock)reject )
{
    @try
    {
        ADAuthenticationError *error;
        ADAuthenticationContext *authContext = [ADAuthenticationContext authenticationContextWithAuthority:authority
                                                                                         validateAuthority:validateAuthority
                                                                                                     error:&error];
        if (!configurations)
        {
            configurations = [NSMutableDictionary dictionaryWithCapacity:1];
        }

        if (error != nil)
        {
            @throw(error);
        }

        Configuration *configuration = [Configuration configurationWithAuthority:authority
                                                               validateAuthority:validateAuthority
                                                                        clientId:clientId
                                                                     redirectUrl:redirectUrl
                                                                       useBroker:useBroker
                                                                     authContext:authContext];

        currentConfiguration = configuration;
        [configurations setObject:configuration forKey:authority];
        resolve( @"success" );

    }
    @catch (ADAuthenticationError *error)
    {
        reject( [[NSString alloc] initWithFormat:@"%d", error.code], error.errorDetails, error );
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
RCT_REMAP_METHOD(acquireTokenAsync,
                 resourceUrl:(NSString *)resourceUrl
                 loginHint:(NSString *) loginHint
                 extraQueryParameters:(NSString *) extraQueryParameters
                 forceLogin:(BOOL) forceLogin
                 resolver:(RCTPromiseResolveBlock)resolve
                 rejecter:(RCTPromiseRejectBlock)reject ){

    if(currentConfiguration){
        @try{
            NSURL *urlRedirectUri = [NSURL URLWithString:currentConfiguration.redirectUrl];
            ADAuthenticationContext* authContext = [currentConfiguration getAuthContext];
            NSString* userId = [AdalUtils GetUserIdFromCache:loginHint authContext:authContext];

            // `x-msauth-` redirect url prefix means we should use brokered authentication
            // https://github.com/AzureAD/azure-activedirectory-library-for-objc#brokered-authentication
            authContext.credentialsType = (urlRedirectUri.scheme && [urlRedirectUri.scheme hasPrefix: @"x-msauth-"]) ? AD_CREDENTIALS_AUTO : AD_CREDENTIALS_EMBEDDED;

            dispatch_async(dispatch_get_main_queue(), ^{
                [authContext
                        acquireTokenWithResource:resourceUrl
                                        clientId:currentConfiguration.clientId
                                     redirectUri:urlRedirectUri
                                  promptBehavior:forceLogin ? AD_FORCE_PROMPT : AD_PROMPT_AUTO
                                          userId:userId
                            extraQueryParameters:extraQueryParameters
                                 completionBlock:^(ADAuthenticationResult *result) {

                                     NSMutableDictionary *msg = [UserInfoSerialization AuthenticationResultToDictionary: result];
                                     if ( AD_SUCCEEDED != result.status ) {
                                         reject( [[NSString alloc] initWithFormat:@"%d", result.error.code], result.error.errorDetails, result.error );
                                     } else {
                                         resolve(msg);
                                     }
                                 }];
            });
        }
        @catch (ADAuthenticationError *error)
        {
            reject( [[NSString alloc] initWithFormat:@"%d", error.code], error.errorDetails, error );
        }
    }
    else {
        notConfigureError(reject);
    }
}

/**
 * @param resourceUrl required resource identifier.
 * @param loginHint   Optional if validateAuthority == null. It is used for cache and as a loginhint at
 *                    authentication.
 * @param promise     Callback Promise which is used to send results back to JS
 */
RCT_REMAP_METHOD(acquireTokenSilentAsync,
                 resourceUrl:(NSString *)resourceUrl
                 loginHint:(NSString *) loginHint
                 resolver:(RCTPromiseResolveBlock)resolve
                 rejecter:(RCTPromiseRejectBlock)reject ) {

    if(currentConfiguration){
        @try {
            ADAuthenticationContext* authContext = [currentConfiguration getAuthContext];
            NSString* userId = [AdalUtils GetUserIdFromCache:loginHint authContext:authContext];

            dispatch_async(dispatch_get_main_queue(), ^{
                [authContext acquireTokenSilentWithResource:resourceUrl
                                                   clientId:currentConfiguration.clientId
                                                redirectUri:nil
                                                     userId:userId
                                            completionBlock:^(ADAuthenticationResult *result) {
                                                NSMutableDictionary *msg = [UserInfoSerialization AuthenticationResultToDictionary:result];

                                                if (AD_SUCCEEDED != result.status) {
                                                    reject([[NSString alloc] initWithFormat:@"%d", result.error.code], result.error.errorDetails, result.error);
                                                } else {
                                                    resolve(msg);
                                                }
                                            }];
            });

        }
        @catch (ADAuthenticationError *error)
        {
            reject( [[NSString alloc] initWithFormat:@"%d", error.code], error.errorDetails, error );
        }
    }
    else {
        notConfigureError(reject);
    }
}

RCT_REMAP_METHOD(clearCoockieAndCache,
                 resolver:(RCTPromiseResolveBlock)resolve
                 rejecter:(RCTPromiseRejectBlock)reject){
    @try {
        ADKeychainTokenCache* cacheStore = [ADKeychainTokenCache new];

        dispatch_async(dispatch_get_main_queue(), ^{
            ADAuthenticationError *error;
            //get all items from cache
            NSArray *cacheItems = [cacheStore allItems:&error];


            if (error != nil)
            {
                @throw(error);
            }
            for (ADTokenCacheItem*  item in cacheItems)
            {
                [cacheStore removeItem:item error: &error];

                if (error != nil)
                {
                    @throw(error);
                }
            }
            resolve(@"success");
        });

    }
    @catch (ADAuthenticationError *error)
    {
        reject( [[NSString alloc] initWithFormat:@"%d", error.code], error.errorDetails, error );
    }
}

static void notConfigureError(RCTPromiseRejectBlock reject)
{
    reject(@"INVALID_CONFIGURATION", @"Initialized Configure() method", nil);
}

@end

