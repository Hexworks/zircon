package org.codetome.zircon.api.resource

import org.junit.Test

class ResourcesTest {

    @Test
    fun shouldBeAbleToLoadResources() {
        REXPaintResource
                .loadREXFile(this.javaClass.getResourceAsStream("/rex_files/cp437_table.xp"))
    }
}
