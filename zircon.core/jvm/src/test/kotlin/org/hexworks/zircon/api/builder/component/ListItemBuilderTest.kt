package org.hexworks.zircon.api.builder.component

import org.assertj.core.api.Assertions
import org.hexworks.zircon.api.component.ListItem
import org.junit.Before
import org.junit.Test

class ListItemBuilderTest : ComponentBuilderTest<ListItem, ListItemBuilder>() {

    override lateinit var target: ListItemBuilder

    @Before
    override fun setUp() {
        target = ListItemBuilder.newBuilder()
    }

    @Test
    override fun shouldProperlyApplyTitle() {
        target.withTitle(TITLE_FOO)

        Assertions.assertThat(target.title).isEmpty()
    }
}

