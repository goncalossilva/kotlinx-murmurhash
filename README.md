# kotlinx-murmurhash

[![badge-version]](https://search.maven.org/search?q=g:com.goncalossilva%20a:murmurhash*)
![badge-jvm][badge-jvm]
![badge-js][badge-js]
![badge-nodejs][badge-nodejs]
![badge-wasm][badge-wasm]
![badge-wasmi][badge-wasmi]
![badge-android][badge-android]
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

Pull requests are welcome! See [MurmurHash3Test](src/commonTest/kotlin/MurmurHash3Test.kt) for an example of how to leverage hashes computed by the canonical C++ implementation for testing.

Feel free to contribute earlier versions, such as MurmurHash2 and MurmurHash1.  

## Acknowledgements

Testing using a wordlist and pre-computed hashes of the canonical C++ implementation is inspired by [@sangupta](https://github.com/sangupta)'s [murmur](https://github.com/sangupta/murmur) Java implementation.

## License

Released under the [MIT License](https://opensource.org/licenses/MIT).

[badge-version]: https://img.shields.io/maven-central/v/com.goncalossilva/murmurhash?style=flat
[badge-ios]: https://img.shields.io/badge/platform-ios-CDCDCD.svg?style=flat
[badge-js]: https://img.shields.io/badge/platform-js-F8DB5D.svg?style=flat
[badge-nodejs]: https://img.shields.io/badge/platform-nodejs-68a063.svg?style=flat
[badge-jvm]: https://img.shields.io/badge/platform-jvm-DB413D.svg?style=flat
[badge-android]: https://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat
[badge-linux]: https://img.shields.io/badge/platform-linux-2D3F6C.svg?style=flat
[badge-windows]: https://img.shields.io/badge/platform-windows-4D76CD.svg?style=flat
[badge-macos]: https://img.shields.io/badge/platform-macos-111111.svg?style=flat
[badge-watchos]: https://img.shields.io/badge/platform-watchos-C0C0C0.svg?style=flat
[badge-tvos]: https://img.shields.io/badge/platform-tvos-808080.svg?style=flat
[badge-wasm]: https://img.shields.io/badge/platform-wasm-624FE8.svg?style=flat
[badge-wasmi]: https://img.shields.io/badge/platform-wasi-626FFF.svg?style=flat
