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

import AzureAdal from 'react-native-azure-adal';

const authority = "https://login.windows.net/ariaserver.onmicrosoft.com";
const resourceUri = "https://ariamobileappproxy-ariaserver.msappproxy.net/VMS.ARIAMobile.DummryService/";

const clientId = 'f00ae3c0-172f-4978-a409-7d7bb29b1026';
const redirectUri = "http://TodoListClient"; //"x-msauth-awesomeproject://com.varian.awesomeproject"; //

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
            let isConfigure =  await AzureAdal.configure(authority, false, clientId, redirectUri, false);
            if(isConfigure) {
                let userDetails = await AzureAdal.getTokenAsync(resourceUri);
                console.log(userDetails);
            }
        }
        catch (error) {
            console.log(error);
        }
    }
    
    async loginWithBroker() {
        const redirectUri = "msauth://com.example/8BhOF7pgEpduSQKBKziiWZDhIVA%3D"; //"msauth://com.example/9maof%2B5SYhc8dc%2Fni%2BFefl6unpw%3D"; //"x-msauth-awesomeproject://com.varian.awesomeproject"; //
        try {
            let isConfigure =  await AzureAdal.configure(authority, false, clientId, redirectUri, true);
            if(isConfigure) {
                let userDetails = await AzureAdal.getTokenAsync(resourceUri);
                console.log(userDetails);
            }
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
                    onPress={this.loginWithBroker.bind(this)}
                    title="Login With Broker"
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
