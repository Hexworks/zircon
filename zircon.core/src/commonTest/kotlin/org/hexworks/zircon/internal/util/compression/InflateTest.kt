package org.hexworks.zircon.internal.util.compression

import io.kotest.matchers.shouldBe
import kotlin.test.Test

/**
 * Vectors were produced with Python's zlib/gzip/zipfile modules.
 */
class InflateTest {

    private val text = "hello hello hello hello zircon zircon zircon! ".repeat(3)
    private val big = "the quick brown fox jumps over the lazy dog 0123456789 ".repeat(20)

    @Test
    fun inflatesFixedHuffmanBlock() {
        Inflate.inflate("cb48cdc9c957c8c020ab328b92f3f35029452ceaa8a71a00".hexToByteArray())
            .decodeToString() shouldBe text
    }

    @Test
    fun inflatesDynamicHuffmanBlock() {
        Inflate.inflate(
            ("2bc94855282ccd4cce56482aca2fcf5348cbaf50c82acd2d2856c82f4b2d5228014ae72456552aa4e4a72b18" +
                "181a199b989a995b5882c547b58d6a1bd536aa0dae0d00").hexToByteArray()
        ).decodeToString() shouldBe big
    }

    @Test
    fun inflatesStoredBlock() {
        Inflate.inflate("010c00f3ff73746f72656420626c6f636b".hexToByteArray())
            .decodeToString() shouldBe "stored block"
    }

    @Test
    fun decompressesGzipWithFileName() {
        val gz = ("1f8b08080000000002ff612e74787400cb48cdc9c957c8c020ab328b92f3f35029452ceaa8a71a00" +
            "a7939fb28a000000").hexToByteArray()
        Gzip.isGzip(gz) shouldBe true
        Gzip.decompress(gz).decodeToString() shouldBe text
    }

    @Test
    fun readsStoredAndDeflatedZipEntries() {
        val zip = ("504b030414000000000000002100c6a91e950c0000000c0000000a00000073746f7265642e74787473746f72" +
            "656420656e747279504b030414000000080020a85c5d7db4d318440000004c040000100000006469722f6465" +
            "666c617465642e7478742bc94855282ccd4cce56482aca2fcf5348cbaf50c82acd2d2856c82f4b2d5228c948" +
            "55c849acaa5448c94f573030343236313533b7b0048b8f6a1bd536aa6d541b5c1b00504b01021403140000" +
            "00000000002100c6a91e950c0000000c0000000a000000000000000000000080010000000073746f7265642e" +
            "747874504b0102140314000000080020a85c5d7db4d318440000004c0400001000000000000000000000008001" +
            "340000006469722f6465666c617465642e747874504b0506000000000200020076000000a60000000000")
            .hexToByteArray()
        val archive = ZipArchive(zip)
        archive.names shouldBe setOf("stored.txt", "dir/deflated.txt")
        archive.read("stored.txt").decodeToString() shouldBe "stored entry"
        archive.read("dir/deflated.txt").decodeToString() shouldBe big
    }
}
