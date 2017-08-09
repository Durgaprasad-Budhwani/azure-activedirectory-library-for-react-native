#!/bin/bash

library_name=$(node -p "require('./package.json').name")

case "${TRAVIS_OS_NAME}" in
  osx)
    cd example_tmp
    npm install
    react-native unlink $library_name
    react-native link
    cd ios && pod install
  ;;
  linux)
    cd example_tmp
    npm install
    react-native unlink $library_name
    react-native link
  ;;
esac
