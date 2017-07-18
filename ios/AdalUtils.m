//
//  AdalUtils.m
//  RNAzureAdal
//
//  Created by Durgaprasad on 7/10/17.
//

#import "AdalUtils.h"
#import <ADAL/ADAL.h>

@implementation AdalUtils
/**
 * Retrieves userId from Token Cache Store.
 * @param loginHint
 * @param authContext
 * @return userId
 */
+ (NSString *) GetUserIdFromCache:(NSString *)loginHint
                      authContext:(ADAuthenticationContext *)authContext{
    NSString* userId = loginHint;
    
    if (userId && [userId length] > 0)
    {
        ADAuthenticationError *error;
        
        ADKeychainTokenCache* cacheStore = [ADKeychainTokenCache new];
        NSArray *cacheItems = [cacheStore allItems:&error];
        
        if (error == nil)
        {
            for (ADTokenCacheItem *obj in cacheItems)
            {
                if ([userId caseInsensitiveCompare:obj.userInformation.userObjectId] == NSOrderedSame || [userId caseInsensitiveCompare:obj.userInformation.uniqueId] == NSOrderedSame) {
                    return obj.userInformation.userId;
                }
            }
        }
    }
    return userId;
}

@end


