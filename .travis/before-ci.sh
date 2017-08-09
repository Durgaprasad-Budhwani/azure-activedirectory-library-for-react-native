#!/bin/bash

case "${TRAVIS_OS_NAME}" in
  osx)
    example_tmp/node_modules/.bin/appium --session-override > appium.out &
  ;;
  linux)
    android list targets
    echo "no" | android create avd --force -n test -t android-23 --abi armeabi-v7a --skin WVGA800 --device "Nexus 5" --sdcard 512M
    echo "no" | avdmanager create avd --force -n test -t android-23 --abi armeabi-v7a --skin WVGA800 --device "Nexus 5" --sdcard 512M
    android list targets
    emulator -avd test -scale 96dpi -dpi-device 160 -no-audio -no-window &
    android-wait-for-emulator
    sleep 60
    adb shell input keyevent 82 &
    example_tmp/node_modules/.bin/appium --session-override > appium.out &
  ;;
esac
