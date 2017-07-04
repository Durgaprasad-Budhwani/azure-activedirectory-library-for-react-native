/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  View,
  Button
} from 'react-native';


export default class example extends Component {
  
  async onLoginPress() {
    try {
      let isConfigure =  await AzureAdal.configure(authority, false, clientId, redirectUri, false);
      if(isConfigure) {
        let userDetails = await AzureAdal.login(resourceUri);
        console.log(userDetails);
      }
    }
    catch (error) {
      console.log(error);
    }
  }
  
  async onLoginWithPromptPress() {
    try {
      let isConfigure =  await AzureAdal.configure(authority, false, clientId, redirectUri, true);
      if(isConfigure) {
	let userDetails = await AzureAdal.loginWithPrompt(resourceUri);
	console.log(userDetails);
      }
    }
    catch (error) {
      console.log(error);
    }
  }
  
  async getToken() {
    try {
      let userDetails = await AzureAdal.getTokenAsync(resourceUri);
      console.log(userDetails);
    }
    catch (error) {
      console.log(error);
    }
  }
  
  async logout() {
    AzureAdal.logout();
  }
  
  render() {
    return (
      <View style={styles.container}>
        <View style={{marginBottom:10}}>
	  <Button
	    onPress={this.onLoginPress.bind(this)}
	    title="Login"
	  />
	</View>
	<View style={{marginBottom:10}}>
	  <Button
	    onPress={this.onLoginWithPromptPress.bind(this)}
	    title="Login With Prompt"
	  />
	</View>
	<View style={{marginBottom:10}}>
	  <Button
	    onPress={this.getToken.bind(this)}
	    title="Get Token"
	  />
	</View>
	<View style={{marginBottom:10}}>
	  <Button
	    onPress={this.logout.bind(this)}
	    title="Logout"
	  />
	</View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

AppRegistry.registerComponent('example', () => example);
