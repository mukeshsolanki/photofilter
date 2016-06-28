# Photo Filter
A simple easy to use library that lets you edit pictures on the fly with easy. Photo filter lets you apply a large number of filters all while maintaining maximum image quality. Supports Android 2.3 and higher.

## How to integrate into your app?
Integrating the library into you app is extremely easy. A few changes in the build gradle and your all ready to user Runtime permissions library. Make the following changes to build.gradle inside you app.
```java
.....
dependencies {
  ...
  compile 'com.mukesh:photofilter:1.0.0'
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
<img src="https://raw.githubusercontent.com/mukeshsolanki/photofilter/master/screenshots/Car.png">
<img src="https://raw.githubusercontent.com/mukeshsolanki/photofilter/master/screenshots/Skull.png">
### Result
<img src="https://raw.githubusercontent.com/mukeshsolanki/photofilter/master/screenshots/Car-applyShadingFilter1.png"><img src="https://raw.githubusercontent.com/mukeshsolanki/photofilter/master/screenshots/Car-boost2.png"><br />
<img src="https://raw.githubusercontent.com/mukeshsolanki/photofilter/master/screenshots/Skull-createContrast1.png">
<img src="https://github.com/mukeshsolanki/photofilter/blob/master/screenshots/Skull-createShadow.png"><br />

You can check out more results [here](https://github.com/mukeshsolanki/photofilter/tree/master/screenshots)
