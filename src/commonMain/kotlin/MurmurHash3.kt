package com.goncalossilva.murmurhash

public class MurmurHash3(private val seed: UInt = 0u) {
    public fun hash32x86(key: ByteArray): UInt {
        var h = seed
        val len = key.size
        val nblocks = len shr 2

        for (i in 0 until nblocks) {
            val index = i shl 2
            val k = key.getUInt(index) or
                (key.getUInt(index + 1) shl 8) or
                (key.getUInt(index + 2) shl 16) or
                (key.getUInt(index + 3) shl 24)

            h = h xor scramble(k)
            h = h.rotateLeft(R2)
            h = h * M + N
        }

        val index = nblocks shl 2
        val rem = len - index
        var k = 0u
        if (rem == 3) {
            k = k xor (key.getUInt(index + 2) shl 16)
        }
        if (rem >= 2) {
            k = k xor (key.getUInt(index + 1) shl 8)
        }
        if (rem >= 1) {
            k = k xor key.getUInt(index)
            h = h xor scramble(k)
        }

        h = h xor len.toUInt()

        h = h xor (h shr 16)
        h *= 0x85ebca6bu
        h = h xor (h shr 13)
        h *= 0xc2b2ae35u
        h = h xor (h shr 16)
        return h
    }

    private fun scramble(karg: UInt): UInt {
        var k = karg
        k *= C1
        k = k.rotateLeft(R1)
        k *= C2
        return k
    }

    private fun ByteArray.getUInt(index: Int) = get(index).toUInt()

    private companion object {
        private const val C1: UInt = 0xcc9e2d51u
        private const val C2: UInt = 0x1b873593u

        private const val R1: Int = 15
        private const val R2: Int = 13

        private const val M: UInt = 5u
        private const val N: UInt = 0xe6546b64u
    }
}
