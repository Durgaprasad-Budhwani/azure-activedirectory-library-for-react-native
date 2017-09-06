#!/bin/bash

case "${TRAVIS_OS_NAME}" in
  osx)
    example_tmp/node_modules/.bin/appium --session-override > appium.out &
  ;;
  linux)
    android list targets
    echo "android list avd"
    android list avd
    android list sdk --extended --no-ui --all
    echo "y" | android update sdk -a --no-ui --filter sys-img-armeabi-v7a-android-25,sys-img-x86_64-android-25   
    echo y | sdkmanager --verbose "system-images;android-25;google_apis;x86"
    echo "n" | android create avd -t "android-25" -n test --abi "default/armeabi-v7a"
    android list targets
    android list avd
    emulator -list-avds
    emulator -avd test -scale 96dpi -dpi-device 160 -no-audio -no-window
    android-wait-for-emulator
    sleep 60
    adb shell input keyevent 82 &
    example_tmp/node_modules/.bin/appium --session-override > appium.out &
  ;;
esac
