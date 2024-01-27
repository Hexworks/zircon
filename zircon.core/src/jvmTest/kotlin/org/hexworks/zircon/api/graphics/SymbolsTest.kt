package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.internal.util.CP437Index
import org.junit.Test

class SymbolsTest {

    @Test
    fun shouldBeAbleToAddAllSymbolsUsingACP437Tileset() {

        Symbols::class.members.filter { it.isFinal }.forEach {
            // should not throw an exception
            (it.call() as Char).CP437Index()
        }

    }
}
