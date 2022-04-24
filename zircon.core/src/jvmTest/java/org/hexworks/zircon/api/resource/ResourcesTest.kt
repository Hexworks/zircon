package org.hexworks.zircon.api.resource

import org.junit.Test

class ResourcesTest {

    @Test
    fun shouldBeAbleToLoadResources() {
        REXPaintResources
            .loadREXFile(this.javaClass.getResourceAsStream("/rex_files/cp437_table.xp"))
    }
}
