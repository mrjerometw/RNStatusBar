/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  Button,
  NativeModules
} from 'react-native';
var statusBar = NativeModules.RNStatusBar;
var bShow = false;
export default class RNStatusBar extends Component {
  render() {
    return (
      <View style={styles.container}>
        <Button
          onPress={ function(){ 
             bShow = !bShow;
             statusBar.showStatusBar(bShow, function () {
                 console.log('showStatusBar');
             });
          }}
          title="Demo"
          color="#841584"/> 
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

AppRegistry.registerComponent('RNStatusBar', () => RNStatusBar);
