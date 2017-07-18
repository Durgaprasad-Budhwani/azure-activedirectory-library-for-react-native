//
//  Configuration.h
//  RNAzureAdal
//
//  Created by Durgaprasad on 7/7/17.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <ADAL/ADAL.h>

@interface Configuration : NSObject {
    NSString* _authority;
    BOOL _validateAuthority;
    NSString* _clientId;
    NSString* _redirectUrl;
    BOOL _useBroker;
    ADAuthenticationContext* _authContext;
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
- (id) initWithConfiguration:(NSString*) authority
           validateAuthority:(BOOL)validateAuthority
                    clientId:(NSString*)clientId
                 redirectUrl:(NSString*)redirectUrl
                   useBroker:(BOOL)useBroker
                 authContext:(ADAuthenticationContext*)authContext;

/**
 * Get authentication context
 * @return authenticationContext
 */
- (ADAuthenticationContext*) getAuthContext;

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
                                   authContext:(ADAuthenticationContext*)authContext;

@property NSString* authority;
@property BOOL validateAuthority;
@property NSString* clientId;
@property NSString* redirectUrl;
@property BOOL useBroker;
@property ADAuthenticationContext* authContext;

@end
