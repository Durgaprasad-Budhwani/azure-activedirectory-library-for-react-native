
# react-native-azure-adal

## Getting started

`$ npm install react-native-azure-adal --save`

### Mostly automatic installation

`$ react-native link react-native-azure-adal`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-azure-adal` and add `RNAzureAdal.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNAzureAdal.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNAzureAdalPackage;` to the imports at the top of the file
  - Add `new RNAzureAdalPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-azure-adal'
  	project(':react-native-azure-adal').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-azure-adal/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-azure-adal')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNAzureAdal.sln` in `node_modules/react-native-azure-adal/windows/RNAzureAdal.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Com.Reactlibrary.RNAzureAdal;` to the usings at the top of the file
  - Add `new RNAzureAdalPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNAzureAdal from 'react-native-azure-adal';

// TODO: What to do with the module?
RNAzureAdal;
```
  