package com.goncalossilva.murmurhash

import com.goncalossilva.resources.Resource
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class MurmurHash3Test {
    @Test
    fun hash32x86Words() {
        val murmurHash3 = MurmurHash3(seed = wordSeed)
        words.zip(wordResults32x86).forEach { (word,  hash) ->
            assertEquals(hash,  murmurHash3.hash32x86(word.encodeToByteArray()),  word)
        }
    }

    @Test
    fun hash32x86Bytes() {
        val murmurHash3 = MurmurHash3()
        assertEquals(1546271276u, murmurHash3.hash32x86(randomBytes))

        arrayOf(
            0u, 2941713443u, 915381745u, 3559983877u, 1271125654u, 3252701403u, 3090445677u,
            735845843u, 138310876u, 2376028632u, 1399647898u, 3168624987u, 2067593280u, 1220975287u,
            1941281084u, 3005454116u, 942412060u, 3676793713u, 4025420649u, 2649336034u,
            1162379906u, 2334841719u, 2438194101u, 1980513522u, 1174612855u, 905810751u,
            1044578220u, 2536480607u, 3803573383u, 839836946u, 3859952881u, 2044851178u,
        ).forEachIndexed { i, result ->
            assertEquals(result, murmurHash3.hash32x86(randomBytes.copyOf(i)))
        }
    }

    @Test
    fun hash32x86TrailingNegativeBytes() {
        val murmurHash3 = MurmurHash3()
        assertEquals(4251775245u, murmurHash3.hash32x86(byteArrayOf(-1)))
        assertEquals(3712929428u, murmurHash3.hash32x86(byteArrayOf(0, -1)))
        assertEquals(922088087u, murmurHash3.hash32x86(byteArrayOf(0, 0, -1)))
        assertEquals(2985399708u, murmurHash3.hash32x86(byteArrayOf(-1, 0)))
        assertEquals(3931187626u, murmurHash3.hash32x86(byteArrayOf(-1, 0, 0)))
        assertEquals(4069899234u, murmurHash3.hash32x86(byteArrayOf(0, -1, 0)))
    }

    @Test
    fun hash128x86Words() {
        val murmurHash3 = MurmurHash3(seed = wordSeed)
        words.zip(wordResults128x86).forEach { (word,  hash) ->
            assertContentEquals(hash,  murmurHash3.hash128x86(word.encodeToByteArray()),  word)
        }
    }

    @Test
    fun hash128x86Bytes() {
        val murmurHash3 = MurmurHash3()
        assertContentEquals(
            arrayOf(2182933960u, 1821170276u, 3414282138u, 1669651681u),
            murmurHash3.hash128x86(randomBytes)
        )

        arrayOf(
            arrayOf(0u, 0u, 0u, 0u),
            arrayOf(4148545660u, 2031603913u, 2031603913u, 2031603913u),
            arrayOf(2328638786u, 2424050002u, 2424050002u, 2424050002u),
            arrayOf(2530296407u, 175037985u, 175037985u, 175037985u),
            arrayOf(2237787759u, 1385972869u, 1385972869u, 1385972869u),
            arrayOf(1961865644u, 920549674u, 2416600236u, 2416600236u),
            arrayOf(3547878919u, 3353045904u, 3565891809u, 3565891809u),
            arrayOf(2504669210u, 1620223567u, 2650476085u, 2650476085u),
            arrayOf(640542057u, 3369957023u, 2844448333u, 2844448333u),
            arrayOf(3407085561u, 2206762367u, 4090305544u, 3095495425u),
            arrayOf(3593397724u, 2670970887u, 318550671u, 3612846912u),
            arrayOf(2444161634u, 2363002900u, 2444095791u, 3836766641u),
            arrayOf(2107077555u, 2709484360u, 2654810428u, 3410740372u),
            arrayOf(484406666u, 3851641058u, 1460561375u, 2128853594u),
            arrayOf(717512668u, 1045959578u, 1044663858u, 4091193076u),
            arrayOf(2060302206u, 3781382062u, 2183132824u, 2264430114u),
            arrayOf(226713589u, 138105254u, 1955762893u, 1285964138u),
            arrayOf(3384174574u, 107632011u, 1778982585u, 688204767u),
            arrayOf(2262271945u, 2462409740u, 880935058u, 3082427021u),
            arrayOf(2474222149u, 3680004554u, 4028282057u, 1979282339u),
            arrayOf(929421067u, 2249307353u, 59284062u, 2085680620u),
            arrayOf(596133403u, 4197449997u, 1018668871u, 3361572487u),
            arrayOf(2814010810u, 3823956888u, 494341763u, 1186692945u),
            arrayOf(1071846054u, 4170919060u, 2577746812u, 2193416593u),
            arrayOf(619524083u, 2196792796u, 580719560u, 984858388u),
            arrayOf(2655904863u, 234635312u, 320056576u, 389282208u),
            arrayOf(1602768065u, 2813274545u, 2840246406u, 1120580694u),
            arrayOf(1669106376u, 4137953464u, 3485788100u, 3956126422u),
            arrayOf(2538124088u, 3123912027u, 2083825439u, 3441014307u),
            arrayOf(4113683680u, 2813551301u, 496088474u, 29157598u),
            arrayOf(3901795648u, 1578210377u, 1720266673u, 3481030617u),
            arrayOf(2555447758u, 3219187397u, 3517270604u, 2719628846u),
        ).forEachIndexed { i, result ->
            assertContentEquals(result, murmurHash3.hash128x86(randomBytes.copyOf(i)))
        }
    }

    @Test
    fun hash128x64Words() {
        val murmurHash3 = MurmurHash3(seed = wordSeed)
        words.zip(wordResults128x64).forEach { (word,  hash) ->
            assertContentEquals(hash,  murmurHash3.hash128x64(word.encodeToByteArray()),  word)
        }
    }

    @Test
    fun hash128x64Bytes() {
        val murmurHash3 = MurmurHash3()
        assertContentEquals(
            arrayOf(1972113670104592209uL, 5171809317673151911uL),
            murmurHash3.hash128x64(randomBytes)
        )

        arrayOf(
            arrayOf(0uL, 0uL),
            arrayOf(15638090232629168493uL, 15914959479678891273uL),
            arrayOf(17162168602708311310uL, 10219802899915089796uL),
            arrayOf(1645529003294647142uL, 4109127559758330427uL),
            arrayOf(14328764957505610851uL, 10083841413387508874uL),
            arrayOf(2559943399590596158uL, 4738005461125350075uL),
            arrayOf(16794984042117998965uL, 13060664818785327155uL),
            arrayOf(12238700113018736007uL, 7862371518025305074uL),
            arrayOf(13296720595285905279uL, 8346305334874564507uL),
            arrayOf(7658274117911906792uL, 13483829414327147451uL),
            arrayOf(1309458104226302269uL, 570003296096149119uL),
            arrayOf(7440169453173347487uL, 14957398292642737876uL),
            arrayOf(12747959775097350264uL, 3595618450161835420uL),
            arrayOf(14624169280971479174uL, 6878153771369862041uL),
            arrayOf(3705084673301918328uL, 3202155281274291907uL),
            arrayOf(11649577329781044685uL, 13999472980056000019uL),
            arrayOf(5240533565589385084uL, 12871262888420793289uL),
            arrayOf(9979123942326902188uL, 11996113706458437148uL),
            arrayOf(3632866961828686471uL, 12489048097620238116uL),
            arrayOf(11996460425632280477uL, 10538111359335033557uL),
            arrayOf(226350826556351719uL, 8225586794606475685uL),
            arrayOf(16063747849212571215uL, 2188369078123678011uL),
            arrayOf(17109199311350770791uL, 7004253486151757299uL),
            arrayOf(2889033453638709716uL, 14347234740555650242uL),
            arrayOf(9801793136899954662uL, 13302221154069933285uL),
            arrayOf(12818172208454030843uL, 17607723072054419529uL),
            arrayOf(13219969406416339170uL, 17941488112515282114uL),
            arrayOf(1337107025517938142uL, 3260952073019398505uL),
            arrayOf(9149852874328582511uL, 1880188360994521535uL),
            arrayOf(14410786085349669770uL, 10737686222943060836uL),
            arrayOf(14604150250403220801uL, 3805147088291453755uL),
            arrayOf(4030161393619149616uL, 15633140292397096378uL),
        ).forEachIndexed { i, result ->
            assertContentEquals(result, murmurHash3.hash128x64(randomBytes.copyOf(i)))
        }
    }

    companion object {
        /**
         * English wordlist from sangupta/murmur:
         * https://github.com/sangupta/murmur/blob/592694f538a87d634418de5f849aed768a758147/src/test/resources/english/english-wordlist.txt
         */
        private val words: List<String> by lazy {
            Resource("src/commonTest/resources/wordlist.txt")
                .readText()
                .trim()
                .lines()
        }

        private val wordSeed = 0x7f3a21eau

        /**
         * Hashes computed by the canonical C++ implementation with the seed above:
         * https://github.com/aappleby/smhasher/blob/61a0530f28277f2e850bfc39600ce61d02b518de/src/MurmurHash3.cpp#L94-L146
         */
        private val wordResults32x86: List<UInt> by lazy {
            Resource("src/commonTest/resources/murmurhash3-32x86.txt")
                .readText()
                .trim()
                .lines()
                .map { line ->
                    line.toUInt()
                }
        }

        /**
         * Hashes computed by the canonical C++ implementation with the seed above:
         * https://github.com/aappleby/smhasher/blob/61a0530f28277f2e850bfc39600ce61d02b518de/src/MurmurHash3.cpp#L150-L251
         */
        private val wordResults128x86: List<Array<UInt>> by lazy {
            // Hashes computed by the canonical C++ implementation:
            // https://github.com/aappleby/smhasher/blob/61a0530f28277f2e850bfc39600ce61d02b518de/src/MurmurHash3.cpp#L150-L251
            Resource("src/commonTest/resources/murmurhash3-128x86.txt")
                .readText()
                .trim()
                .lines()
                .map { line ->
                    line.split(",")
                        .map(String::toUInt)
                        .toTypedArray()
                }
        }

        /**
         * Hashes computed by the canonical C++ implementation with the seed above:
         * https://github.com/aappleby/smhasher/blob/61a0530f28277f2e850bfc39600ce61d02b518de/src/MurmurHash3.cpp#L255-L332
         */
        private val wordResults128x64: List<Array<ULong>> by lazy {
            Resource("src/commonTest/resources/murmurhash3-128x64.txt")
                .readText()
                .trim()
                .lines()
                .map { line ->
                    line.split(",")
                        .map(String::toULong)
                        .toTypedArray()
                }
        }

        /**
         * All possible bytes in random order.
         * https://github.com/apache/commons-codec/blob/master/src/test/java/org/apache/commons/codec/digest/MurmurHash3Test.java
         */
        private val randomBytes = arrayOf(
            46, 246, 249, 184, 247, 84, 99, 144, 62, 77, 195, 220, 92, 20, 150, 159, 38, 40, 124,
            252, 185, 28, 63, 13, 213, 172, 85, 198, 118, 74, 109, 157, 132, 216, 76, 177, 173, 23,
            140, 86, 146, 95, 54, 176, 114, 179, 234, 174, 183, 141, 122, 12, 60, 116, 200, 142, 6,
            167, 59, 240, 33, 29, 165, 111, 243, 30, 219, 110, 255, 53, 32, 35, 64, 225, 96, 152,
            70, 41, 133, 80, 244, 127, 57, 199, 5, 164, 151, 49, 26, 180, 203, 83, 108, 39, 126,
            208, 42, 206, 178, 19, 69, 223, 71, 231, 250, 125, 211, 232, 189, 55, 44, 82, 48, 221,
            43, 192, 241, 103, 155, 27, 51, 163, 21, 169, 91, 94, 217, 191, 78, 72, 93, 102, 104,
            105, 8, 113, 100, 143, 89, 245, 227, 120, 160, 251, 153, 145, 45, 218, 168, 233, 229,
            253, 67, 22, 182, 98, 137, 128, 135, 11, 214, 66, 73, 171, 188, 170, 131, 207, 79, 106,
            24, 75, 237, 194, 7, 129, 215, 81, 248, 242, 16, 25, 136, 147, 156, 97, 52, 10, 181, 17,
            205, 58, 101, 68, 230, 1, 37, 0, 222, 88, 130, 148, 224, 47, 50, 197, 34, 212, 196, 209,
            14, 36, 139, 228, 154, 31, 175, 202, 236, 161, 3, 162, 190, 254, 134, 119, 4, 61, 65,
            117, 186, 107, 204, 9, 187, 201, 90, 149, 226, 56, 239, 238, 235, 112, 87, 18, 121, 115,
            138, 123, 210, 2, 193, 166, 158, 15
        ).map {
            it.toByte()
        }.toByteArray()
    }
}
