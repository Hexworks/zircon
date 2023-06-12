package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.ListItem
import org.junit.Before

class ListItemBuilderTest : JvmComponentBuilderTest<ListItem>() {

    override lateinit var target: ListItemBuilder

    @Before
    override fun setUp() {
        target = ListItemBuilder()
    }
}

