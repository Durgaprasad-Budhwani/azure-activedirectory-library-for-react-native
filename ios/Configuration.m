//
//  Configuration.m
//  RNAzureAdal
//
//  Created by Durgaprasad on 7/7/17.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import "Configuration.h"

@implementation Configuration

@synthesize authority = _authority;
@synthesize validateAuthority = _validateAuthority;
@synthesize clientId = _clientId;
@synthesize redirectUrl = _redirectUrl;
@synthesize useBroker = _useBroker;
@synthesize authContext = _authContext;


/**
 * Intialize configuration class
 * @param authority
 * @param validateAuthority
 * @param clientId
 * @param redirectUrl
 * @param useBroker
 * @param authContext
 * @return instance of configuration class
 */
- (id) initWithConfiguration:(NSString*) authority
           validateAuthority:(BOOL)validateAuthority
                    clientId:(NSString*)clientId
                 redirectUrl:(NSString*)redirectUrl
                  useBroker:(BOOL)useBroker
                 authContext:(ADAuthenticationContext*)authContext {
    if (!(self = [super init]))
    {
        return nil;
    }
    _authority = authority;
    _validateAuthority = validateAuthority;
    _clientId = clientId;
    _redirectUrl = redirectUrl;
    _useBroker = useBroker;
    _authContext = authContext;
    
    return self;
}

/**
 * Get authentication context
 * @return authenticationContext
 */
- (ADAuthenticationContext*) getAuthContext
{
    return _authContext;
}

/**
 * Intialize configuration class
 * @param authority
 * @param validateAuthority
 * @param clientId
 * @param redirectUrl
 * @param useBroker
 * @param authContext
 * @return instance of configuration class
 */
+ (Configuration *) configurationWithAuthority:(NSString*) authority
                             validateAuthority:(BOOL)validateAuthority
                                      clientId:(NSString*)clientId
                                   redirectUrl:(NSString*)redirectUrl
                                     useBroker:(BOOL)useBroker
                                   authContext:(ADAuthenticationContext*)authContext{
    return [[Configuration alloc] initWithConfiguration:authority
                                      validateAuthority:validateAuthority
                                               clientId:clientId
                                            redirectUrl:redirectUrl
                                             useBroker:useBroker
                                            authContext:authContext];
}

@end
