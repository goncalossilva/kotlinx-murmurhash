package com.goncalossilva.murmurhash

import com.goncalossilva.resources.Resource
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class MurmurHash3Test {
    private val words: List<String> by lazy {
        // English wordlist from sangupta/murmur:
        // https://github.com/sangupta/murmur/blob/592694f538a87d634418de5f849aed768a758147/src/test/resources/english/english-wordlist.txt
        Resource("src/commonTest/resources/wordlist.txt").readText().trim().lines()
    }

    private val results32x86: List<UInt> by lazy {
        // Hashes computed by the canonical C++ implementation:
        // https://github.com/aappleby/smhasher/blob/61a0530f28277f2e850bfc39600ce61d02b518de/src/MurmurHash3.cpp#L94-L146
        Resource("src/commonTest/resources/murmurhash3-32x86.txt")
            .readText()
            .trim()
            .lines()
            .map { line -> line.toUInt() }
    }

    private val results128x86: List<Array<UInt>> by lazy {
        // Hashes computed by the canonical C++ implementation:
        // https://github.com/aappleby/smhasher/blob/61a0530f28277f2e850bfc39600ce61d02b518de/src/MurmurHash3.cpp#L150-L251
        Resource("src/commonTest/resources/murmurhash3-128x86.txt")
            .readText()
            .trim()
            .lines()
            .map { line -> line.split(",").map(String::toUInt).toTypedArray() }
    }

    private val results128x64: List<Array<ULong>> by lazy {
        // Hashes computed by the canonical C++ implementation:
        // https://github.com/aappleby/smhasher/blob/61a0530f28277f2e850bfc39600ce61d02b518de/src/MurmurHash3.cpp#L255-L332
        Resource("src/commonTest/resources/murmurhash3-128x64.txt")
            .readText()
            .trim()
            .lines()
            .map { line -> line.split(",").map(String::toULong).toTypedArray() }
    }

    private val murmurHash3 = MurmurHash3(seed = 0x7f3a21eau)

    @Test
    fun hash32x86() {
        words.zip(results32x86).forEach { (word, hash) ->
            assertEquals(hash, murmurHash3.hash32x86(word.encodeToByteArray()), word)
        }
    }

    @Test
    fun hash128x86() {
        words.zip(results128x86).forEach { (word, hash) ->
            assertContentEquals(hash, murmurHash3.hash128x86(word.encodeToByteArray()), word)
        }
    }

    @Test
    fun hash128x64() {
        words.zip(results128x64).forEach { (word, hash) ->
            assertContentEquals(hash, murmurHash3.hash128x64(word.encodeToByteArray()), word)
        }
    }
}
