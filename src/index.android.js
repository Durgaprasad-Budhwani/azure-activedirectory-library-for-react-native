/**
 * Created by durgaprasad on 7/4/17.
 */
import {
  NativeModules,
  PermissionsAndroid
} from 'react-native';

const {
  RNAzureAdal
} = NativeModules;

import BaseAzureAdal from './BaseAzureAdal';

export default class AzureAdal extends BaseAzureAdal {
  /**
   *
   * @param authority
   * @param validateAuthority
   * @param clientId
   * @param redirectUrl
   * @param useBroker if true, it will try to use broker based authentication only if broker is present
   */
  configure (authority: String, validateAuthority: Boolean, clientId: String,
	     redirectUrl: String, useBroker: Boolean) {
    const PERMISSION_DENIED_ERROR_CODE = "PERMISSION_DENIED";
    return new Promise(
      async (resolve, reject) => {
	if (useBroker) {
	  try {
	    const granted = await PermissionsAndroid.request(PermissionsAndroid.PERMISSIONS.GET_ACCOUNTS);
	    if (granted === PermissionsAndroid.RESULTS.GRANTED || granted === true) {
	      RNAzureAdal.configure(authority, validateAuthority, clientId, redirectUrl, useBroker);
	      resolve(true);
	    } else {
	      reject(PERMISSION_DENIED_ERROR_CODE);
	    }
	  } catch (err) {
	    console.warn(err)
	  }
	}
	else {
	  RNAzureAdal.configure(authority, validateAuthority, clientId, redirectUrl, useBroker);
	  resolve(true);
	}
      })
  }
}

