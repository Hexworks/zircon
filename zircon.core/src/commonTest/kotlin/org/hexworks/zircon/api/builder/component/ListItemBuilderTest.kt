package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.ListItem
import kotlin.test.BeforeTest

class ListItemBuilderTest : JvmComponentBuilderTest<ListItem>() {

    override lateinit var target: ListItemBuilder

    @BeforeTest
    override fun setUp() {
        target = ListItemBuilder()
    }
}
