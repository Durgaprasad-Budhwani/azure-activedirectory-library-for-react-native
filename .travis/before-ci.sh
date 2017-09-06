#!/bin/bash

case "${TRAVIS_OS_NAME}" in
  osx)
    example_tmp/node_modules/.bin/appium --session-override > appium.out &
  ;;
  linux)
    android list targets
    touch /home/travis/.android/repositories.cfg
    touch "$ANDROID_SDK/licenses/android-sdk-license"
    echo -e "\n8933bad161af4178b1185d1a37fbf41ea5269c55" > "$ANDROID_SDK/licenses/android-sdk-license"
    echo y | sdkmanager --verbose "system-images;android-25;google_apis;x86"
    echo "no" | android -v create avd -t "android-25" -n test --sdcard 512M -f
    android -list-avd
    emulator -list-avds
    emulator -avd test -scale 96dpi -dpi-device 160 -no-audio -no-window
    android-wait-for-emulator
    sleep 60
    adb shell input keyevent 82 &
    example_tmp/node_modules/.bin/appium --session-override > appium.out &
  ;;
esac
