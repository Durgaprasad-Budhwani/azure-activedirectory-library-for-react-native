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
    return RNAzureAdal.acquireTokenAsync(resourceUrl, loginHint, extraQueryParameters);
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
   * @returns {*}
   */
  getTokenAsync (resourceUrl: String) {
    return RNAzureAdal.acquireTokenSilentAsync(resourceUrl);
  }
  
  /**
   * Sign out from your application
   */
  logout () {
    RNAzureAdal.clearCoockieAndCache();
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

