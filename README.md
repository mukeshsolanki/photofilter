<h1 align="center">Photo Filter for Android</h1>
<p align="center">
  <a href="https://android-arsenal.com/api?level=9"> <img src="https://img.shields.io/badge/API-9%2B-blue.svg?style=flat" /></a>
  <a href="https://jitpack.io/#mukeshsolanki/photofilter"> <img src="https://jitpack.io/v/mukeshsolanki/photofilter.svg" /></a>
  <a href="https://android-arsenal.com/details/1/3802"> <img src="https://img.shields.io/badge/Android%20Arsenal-Photo%20Filter-brightgreen.svg?style=flat" /></a>
  <a href="https://travis-ci.org/mukeshsolanki/photofilter"> <img src="https://travis-ci.org/mukeshsolanki/photofilter.svg?branch=master" /></a>
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
        compile 'com.github.mukeshsolanki:photofilter:1.0.1'
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
