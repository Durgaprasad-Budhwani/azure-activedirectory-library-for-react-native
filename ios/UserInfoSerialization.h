//
//  UserInfoSerialization.h
//  RNAzureAdal
//
//  Created by Durgaprasad on 7/10/17.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <ADAL/ADAL.h>

@interface UserInfoSerialization : NSObject

/**
 * Convert ADUserInformation to Dictionary, will be used by react native to pass information to JS
 * @param obj
 * @return NSMutableDictionary
 */
+ (id) UserInfoToDictionary:(ADUserInformation *) obj;

/**
 * Convert ADAuthenticationResult to Dictionary, will be used by react native to pass information to JS
 * @param obj
 * @return
 */
+ (NSMutableDictionary *)AuthenticationResultToDictionary:(ADAuthenticationResult *)obj;

/**
 * Convert ADTokenCacheItem to Dictionary, will be used by react native to pass information to JS
 * @param obj
 * @return NSMutableDictionary
 */
+ (NSMutableDictionary *)AuthenticationSuccessResultToDictionary:(ADTokenCacheItem *)obj;

/**
 * Convert ADAuthenticationError to Dictionary, will be used by react native to pass information to JS
 * @param obj
 * @return NSMutableDictionary
 */
+ (NSMutableDictionary *)AuthenticationErrorToDictionary:(ADAuthenticationError *)obj;


@end
