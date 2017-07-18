# Installation -

## Prerequisites

A working CocoaPods installation [CocoaPods - Getting Started](https://guides.cocoapods.org/using/getting-started.html)

## Step 1

- If CocoaPods is not installed, please run below commands:

```bash
pod init
```

- Add the ADAL ios library to your ios/Podfile file pod `'ADAL', '~> 2.3'`.

```
target 'example' do
  # Uncomment the next line if you're using Swift or would like to use dynamic frameworks
  # use_frameworks!

  # Add ADAL Pod
  pod 'ADAL', '~> 2.3'
  # Pods for example

  target 'exampleTests' do
    inherit! :search_paths
    # Pods for testing
  end

end

```

- Run below command to pull the ios ADAL library down.

```bash
pod install
```


## Caching

iOS

Keychain Setup

Click on your project in the Navigator pane in Xcode. Click on your application target and then the "Capabilities" tab. Scroll down to "Keychain Sharing" and flip the switch on. Add "com.microsoft.adalcache" to that list.

Alternatively you can disable keychain sharing by setting the keychain sharing group to nil. your application's bundle id.

    [[ADAuthenticationSettings sharedInstance] setSharedCacheKeychainGroup:nil];



