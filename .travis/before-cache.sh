#!/bin/bash

case "${TRAVIS_OS_NAME}" in
  osx)
    rm -rf example_tmp/node_modules/react-native-azure-adal
  ;;
  linux)
    rm -rf example_tmp/node_modules/react-native-azure-adal
    rm -f $HOME/.gradle/caches/modules-2/modules-2.lock
  ;;
esac
