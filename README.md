# ![EmojiOne Logo](https://www.emojione.com/images/vectors/emojione-typeface.svg)

[![Release](https://jitpack.io/v/jasonamark/emojione-android.svg)](https://github.com/jasonamark/emojione-android)
[![License](https://img.shields.io/cocoapods/l/emojione-ios.svg?style=flat)](LICENSE.md)

**EmojiOne Android library** to help users find and replace native system emojis with EmojiOne in thier Android app.


## What's Included?

 - This project includes a Java library used to convert emoji into various formats, including conversion to EmojiOne emoji images.
 - The library included here are available free under the MIT license.  Please check the [LICENSE.md](LICENSE.md) file for more details.
 
 
## License to Use EmojiOne Images
 
### EmojiOne Version 3
 
 EmojiOne launched version 3.0 in 2017, which has several licensing options available. PNG 32px, 64px, and 128px as well as 32px and 64px sprites are available for digital use, with attribution. See [emojione.com/developers/free-license](https://www.emojione.com/developers/free-license) for more information on usage and attribution requirements.
 
 *Premium Licenses are available for larger PNG assets and SVG assets, for digital and print use.* See [emojione.com/developers/premium-license](https://www.emojione.com/developers/premium-license) for more information or to obtain a Premium License.
 

## Installation

emojione-android is available through [JitPack](https://jitpack.io). To install
it, simply add the following line to your build.gradle:

```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
dependencies {
    implementation 'com.github.jasonamark:emojione-android:1.1.1'
}
```
and in your manifest add:

```gradle
<uses-permission android:name="android.permission.INTERNET" />
```

Internet is required to download the converted emoji. 

## Contributing
Please see [CONTRIBUTING.md](CONTRIBUTING.md) for more info on contributing to the emojione project. For artwork comments and questions please see the emojione-assets repo.

## Usage
You'll find basic usage examples here in the [/EmojioneExamples/](EmojioneExamples/) directory, and links to usage demos in [USAGE.md](USAGE.md).


## Information

### Bug reports

If you discover any bugs, feel free to create an issue on GitHub. We also welcome the open-source community to contribute to the project by forking it and issuing pull requests.

 *  [https://github.com/jasonamark/emojione-android/issues](https://github.com/jasonamark/emojione-android/issues)


### Contact

If you have any questions, comments, or concerns you are welcome to contact us.

*  [![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/emojione/emojione?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
* [support@emojione.com](mailto:support@emojione.com)
* [http://emojione.com](http://emojione.com)
* [https://twitter.com/emojione](https://twitter.com/emojione)
