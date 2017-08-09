#!/bin/bash

case "${TRAVIS_OS_NAME}" in
  osx)
    example_tmp/node_modules/.bin/appium --session-override > appium.out &
  ;;
  linux)
    android list targets
    sdkmanager "system-images;android-23;google_apis;x86" --licenses
    avdmanager avdmanager create avd -n test --abi armeabi-v7a --sdcard 512M -f -k "system-images;android-23;google_apis;x86"
    android list targets
    emulator -list-avds
    emulator -avd test -scale 96dpi -dpi-device 160 -no-audio -no-window &
    android-wait-for-emulator
    sleep 60
    adb shell input keyevent 82 &
    example_tmp/node_modules/.bin/appium --session-override > appium.out &
  ;;
esac
