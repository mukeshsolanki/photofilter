<h1 align="center">Photo Filter for Android</h1>
<p align="center">
  <a class="badge-align" href="https://www.codacy.com/app/mukeshsolanki/photofilter?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=mukeshsolanki/photofilter&amp;utm_campaign=Badge_Grade"><img src="https://api.codacy.com/project/badge/Grade/a4df9fd87fec408f9fe52641016205ae"/></a>
  <a href="https://jitpack.io/#mukeshsolanki/photofilter"> <img src="https://jitpack.io/v/mukeshsolanki/photofilter.svg" /></a>
  <a href="https://travis-ci.org/mukeshsolanki/photofilter"> <img src="https://travis-ci.org/mukeshsolanki/photofilter.svg?branch=master" /></a>
  <a href="https://opensource.org/licenses/Apache-2.0"><img src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://www.paypal.me/mukeshsolanki"> <img src="https://img.shields.io/badge/paypal-donate-yellow.svg" /></a>
  <br /><br />A simple easy to use library that lets you edit pictures on the fly with easy. Photo filter lets you apply a large number of filters all while maintaining maximum image quality. Supports Android 2.3 and higher.
</p>


## How to integrate into your app?
Integrating the library into you app is extremely easy. A few changes in the build gradle and your all ready to use the library. Make the following changes.

Step 1. Add the JitPack repository to your build file. Add it in your root build.gradle at the end of repositories:

```java
allprojects {
  repositories {
    ...
    maven { url "https://jitpack.io" }
  }
}
```
Step 2. Add the dependency
```java
dependencies {
        implementation 'com.github.mukeshsolanki:photofilter:<latest-version>'
}
```

## How to use the library?
Okay seems like you integrated the library in your project but **how do you use it**? Well its really easy just follow the steps below.

```
 ImageProcessor imageProcessor = new ImageProcessor();
 
 imageProcessor.doInvert(bitmap);
```

That's pretty much it and your all wrapped up.

## Supported Filters (Exposed Methods)
* doHighlightImage
* doInvert
* doGreyScale
* doGamma
* doColorFilter
* createSepiaToningEffect
* decreaseColorDepth
* createContrast
* rotate
* doBrightness
* applyGaussianBlur
* createShadow
* sharpen
* applyMeanRemoval
* smooth
* emboss
* engrave
* boost
* roundCorner
* waterMark
* flip
* tintImage
* applyFleaEffect
* applyBlackFilter
* applySnowEffect
* applyShadingFilter
* applySaturationFilter
* applyHueFilter
* applyReflection
* replaceColor

## Screenshots
### Original
![Original 1](https://raw.githubusercontent.com/mukeshsolanki/photofilter/master/screenshots/Car.png)
![Original 1](https://raw.githubusercontent.com/mukeshsolanki/photofilter/master/screenshots/Skull.png)

### Result
![Result 1](https://raw.githubusercontent.com/mukeshsolanki/photofilter/master/screenshots/Car-applyShadingFilter1.png)
![Result 2](https://raw.githubusercontent.com/mukeshsolanki/photofilter/master/screenshots/Car-boost2.png)
![Result 3](https://raw.githubusercontent.com/mukeshsolanki/photofilter/master/screenshots/Skull-createContrast1.png)
![Result 4](https://raw.githubusercontent.com/mukeshsolanki/photofilter/master/screenshots/Skull-createShadow.png)

You can check out more results [here](https://github.com/mukeshsolanki/photofilter/tree/master/screenshots)

## Author
Maintained by [Mukesh Solanki](https://www.github.com/mukeshsolanki)

## Contribution
[![GitHub contributors](https://img.shields.io/github/contributors/mukeshsolanki/photofilter.svg)](https://github.com/mukeshsolanki/photofilter/graphs/contributors)

* Bug reports and pull requests are welcome.
* Make sure you use [square/java-code-styles](https://github.com/square/java-code-styles) to format your code.

## License
```
Copyright 2018 Mukesh Solanki

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```