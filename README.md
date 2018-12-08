<h1 align="center">Photo Filter for Android</h1>
<p align="center">
  <a class="badge-align" href="https://www.codacy.com/app/mukeshsolanki/photofilter?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=mukeshsolanki/photofilter&amp;utm_campaign=Badge_Grade"><img src="https://api.codacy.com/project/badge/Grade/a4df9fd87fec408f9fe52641016205ae"/></a>
  <a href="https://jitpack.io/#mukeshsolanki/photofilter"> <img src="https://jitpack.io/v/mukeshsolanki/photofilter.svg" /></a>
  <a href="https://circleci.com/gh/mukeshsolanki/photofilter"> <img src="https://circleci.com/gh/mukeshsolanki/photofilter.svg?style=shield" /></a>
  <a href="https://opensource.org/licenses/Apache-2.0"><img src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
</p>

# Support Photo Filter for Android

A simple easy to use library that lets you edit pictures on the fly with easy. Photo filter lets you apply a large number of filters all while maintaining maximum image quality. Supports Android 2.3 and higher. Photo filter is an independent project with ongoing development and support made possible thanks to donations made by [these awesome backers](BACKERS.md#sponsors). If you'd like to join them, please consider:

- [Become a backer or sponsor on Patreon](https://www.patreon.com/mukeshsolanki).
- [One-time donation via PayPal](https://www.paypal.me/mukeshsolanki)

<a href="https://www.patreon.com/bePatron?c=935498" alt="Become a Patron"><img src="https://c5.patreon.com/external/logo/become_a_patron_button.png" /></a>

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
 private var photoFilter = PhotoFilter(effectView, object: OnProcessingCompletionListener{
  override fun onProcessingComplete(bitmap: Bitmap) {
   // Do anything with the bitmap save it or add another effect to it
  }
 })

 photoFilter?.applyEffect(bitmap, AutoFix())
```

That's pretty much it and your all wrapped up.

## Supported Filters
* AutoFix
* Brightness
* Contrast
* CrossProcess
* Documentary
* DuoTone
* Fill-Light
* FishEye
* Flip Horizontally
* Flip Vertically
* Grain
* Grayscale
* Highlight
* Lomoish
* Negative
* None
* Posterize
* Rotate
* Saturate
* Sepia
* Sharpen
* Temperature
* Tint
* Vignette

## Screenshots
### Original
<img src="https://raw.githubusercontent.com/mukeshsolanki/photofilter/master/screen-shot-1.jpg" height="480" width="270"/>

### Result
<img src="https://raw.githubusercontent.com/mukeshsolanki/photofilter/master/screen-shot-2.jpg" height="480" width="270"/>
<img src="https://raw.githubusercontent.com/mukeshsolanki/photofilter/master/screen-shot-3.jpg" height="480" width="270"/>
<img src="https://raw.githubusercontent.com/mukeshsolanki/photofilter/master/screen-shot-4.jpg" height="480" width="270"/>
<img src="https://raw.githubusercontent.com/mukeshsolanki/photofilter/master/screen-shot-5.jpg" height="480" width="270"/>

## Author
Maintained by [Mukesh Solanki](https://www.github.com/mukeshsolanki)

## Contribution
[![GitHub contributors](https://img.shields.io/github/contributors/mukeshsolanki/photofilter.svg)](https://github.com/mukeshsolanki/photofilter/graphs/contributors)

* Bug reports and pull requests are welcome.
* Make sure you use [square/java-code-styles](https://github.com/square/java-code-styles) to format your code.

## License
```
Copyright (c) 2018 Mukesh Solanki

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```