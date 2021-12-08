# kotlinx-murmurhash

[![badge-version]](https://search.maven.org/search?q=g:com.goncalossilva%20a:murmurhash*)
![badge-jvm][badge-jvm]
![badge-js][badge-js]
![badge-ios][badge-ios]
![badge-watchos][badge-watchos]
![badge-tvos][badge-tvos]
![badge-macos][badge-macos]
![badge-windows][badge-windows]
![badge-linux][badge-linux]

Kotlin Multiplatform (KMP) library for [MurmurHash](https://en.wikipedia.org/wiki/MurmurHash), a non-cryptographic hash function for general hash-based lookup focused on simplicity and performance.

## Usage

Add the dependency in `build.gradle.kts`:

```kotlin
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.goncalossilva:murmurhash:<version>")
            }
        }
    }
}
```

Use the provided `MurmurHash*` classes and `hash*` hashing functions.

## Example

```kotlin
MurmurHash3().hash32x86(string.encodeToByteArray())
```

## Contributing

This library is incomplete. It lacks:
- **MurmurHash3's 128-bit versions**
- MurmurHash2
- MurmurHash1

Pull requests that add missing implementations will be accepted! See [MurmurHash3Test](src/commonTest/kotlin/MurmurHash3Test.kt) for an example of how to leverage hashes computed by the canonical C++ implementation for testing. I can generate hashes for new versions, if necessary.

## Acknowledgements

Testing using a wordlist and pre-computed hashes of the canonical C++ implementation is inspired by [@sangupta](https://github.com/sangupta)'s [murmur](https://github.com/sangupta/murmur) Java implementation.

## License

```
MIT License

Copyright (c) 2021 Gonçalo Silva

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

[badge-version]: https://img.shields.io/maven-central/v/com.goncalossilva/murmurhash?style=flat
[badge-android]: https://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat
[badge-ios]: https://img.shields.io/badge/platform-ios-CDCDCD.svg?style=flat
[badge-js]: https://img.shields.io/badge/platform-js-F8DB5D.svg?style=flat
[badge-jvm]: https://img.shields.io/badge/platform-jvm-DB413D.svg?style=flat
[badge-linux]: https://img.shields.io/badge/platform-linux-2D3F6C.svg?style=flat
[badge-windows]: https://img.shields.io/badge/platform-windows-4D76CD.svg?style=flat
[badge-macos]: https://img.shields.io/badge/platform-macos-111111.svg?style=flat
[badge-watchos]: https://img.shields.io/badge/platform-watchos-C0C0C0.svg?style=flat
[badge-tvos]: https://img.shields.io/badge/platform-tvos-808080.svg?style=flat
[badge-wasm]: httpss://img.shields.io/badge/platform-wasm-624FE8.svg?style=flat
