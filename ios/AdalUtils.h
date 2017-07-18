//
//  AdalUtils.h
//  RNAzureAdal
//
//  Created by Durgaprasad Budhwani on 7/10/17.
//

#import <Foundation/Foundation.h>
#import <ADAL/ADAL.h>

@interface AdalUtils : NSObject

/**
 * Retrieves userId from Token Cache Store.
 * @param loginHint
 * @param authContext
 * @return userId
 */
+ (NSString *) GetUserIdFromCache:(NSString *)loginHint
                      authContext:(ADAuthenticationContext *)authContext;

@end
