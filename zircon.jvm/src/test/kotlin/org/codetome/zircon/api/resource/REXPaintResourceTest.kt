package org.codetome.zircon.api.resource

import org.junit.Test

class REXPaintResourceTest {

    @Test
    fun test() {
        REXPaintResource.loadREXFile(this.javaClass.getResourceAsStream("/rex_files/cp437_table.xp"))
    }
}