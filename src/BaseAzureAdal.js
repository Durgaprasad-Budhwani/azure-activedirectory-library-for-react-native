/**
 * Created by durgaprasad on 7/4/17.
 */
import {
  NativeModules
} from 'react-native';

const {
  RNAzureAdal
} = NativeModules;

export default class AzureAdal {
  /**
   * let the user signin with azure ad credentials into your application,
   * if already logged in, it will not ask credentials again
   * @param resourceUrl
   * @param loginHint
   * @param extraQueryParameters
   * @returns Promise
   */
  login (resourceUrl: String, loginHint: String, extraQueryParameters: String) {
    return RNAzureAdal.acquireTokenAsync(resourceUrl, loginHint, extraQueryParameters, false);
  }
  
  /**
   * Prompt the modal to let the user signin with azure ad credentials into your application
   * @param resourceUrl
   * @param loginHint
   * @param extraQueryParameters
   * @returns Promise
   */
  loginWithPrompt (resourceUrl: String, loginHint: String, extraQueryParameters: String) {
    return RNAzureAdal.acquireTokenAsync(resourceUrl, loginHint, extraQueryParameters, true);
  }
  
  /**
   * It will return you token
   * It will internally call acquireTokenSilentAsync of azure ad authentication context
   * Note:- One time logged in required
   * @param resourceUrl
   * @returns Promise
   */
  getTokenAsync (resourceUrl: String, loginHint:String) {
    return RNAzureAdal.acquireTokenSilentAsync(resourceUrl, loginHint);
  }
  
  /**
   * Sign out from your application
   * @returns Promise
   */
  logout (redirectUri) {
      RNAzureAdal.clearCoockieAndCache();
      const promise = fetch(
          "https://login.windows.net/common/oauth2/logout?post_logout_redirect_uri=" +
          redirectUri
      ).then(response => true);
      return promise;
  }
  
  /**
   * Switch to cache configure
   * @param authority
   * @returns Promise
   */
  switchConfiguration (authority: String) {
    return RNAzureAdal.swtichConfiguration(authority);
  }
}

