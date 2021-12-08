package com.goncalossilva.murmurhash

public class MurmurHash3(private val seed: Int = 0) {
    public fun hash32x86(key: ByteArray): Int {
        val len = key.size
        var h = seed

        var k: Int
        for (i in 0 until (len ushr 2) * 4 step 4) {
            k = key[i].toInt() or
                (key[i + 1].toInt() shl 8) or
                (key[i + 2].toInt() shl 16) or
                (key[i + 3].toInt() shl 24)

            h = h xor scramble(k)
            h = (h shl R2) or (h ushr 32 - R2)
            h = h * M + N
        }

        k = 0
        for (i in len % 4 downTo 1) {
            k = k shl 8
            k = k or key[len - i].toInt()
        }
        h = h xor scramble(k)

        h = h xor len

        h = h xor (h ushr 16)
        h *= 0x85ebca6b.toInt()
        h = h xor (h ushr 13)
        h *= 0xc2b2ae35.toInt()
        h = h xor (h ushr 16)
        return h
    }

    private fun scramble(karg: Int): Int {
        var k = karg
        k *= C1
        k = (k shl R1) or (k ushr 32 - R1)
        k *= C2
        return k
    }

    private companion object {
        private const val C1: Int = 0xcc9e2d51.toInt()
        private const val C2: Int = 0x1b873593

        private const val R1: Int = 15
        private const val R2: Int = 15

        private const val M: Int = 5
        private const val N: Int = 0xe6546b64.toInt()
    }
}
