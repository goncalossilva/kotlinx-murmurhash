package com.goncalossilva.murmurhash

import com.goncalossilva.resources.Resource
import kotlin.test.Test
import kotlin.test.assertEquals

class MurmurHash3Test {
    private val words: List<String> by lazy {
        // English wordlist from sangupta/murmur:
        // https://github.com/sangupta/murmur/blob/592694f538a87d634418de5f849aed768a758147/src/test/resources/english/english-wordlist.txt
        Resource("src/commonTest/resources/wordlist.txt").readText().lines()
    }

    private val results32Bit: List<Int> by lazy {
        // Hashes computed by the canonical C++ implementation.
        Resource("src/commonTest/resources/murmurhash3-32x86.txt").readText().lines().map {
            it.toInt()
        }
    }

    private val murmurHash3 = MurmurHash3(seed = 0x7f3a21ea)

    @Test
    fun hash32Bit() {
        words.zip(results32Bit).forEach { (word, hash) ->
            assertEquals(hash, murmurHash3.hash32x86(word.encodeToByteArray()))
        }
    }
}
