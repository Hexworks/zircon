package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.ToggleButton
import org.hexworks.zircon.api.ComponentDecorations.box
import org.junit.Before
import org.junit.Test

class ToggleButtonBuilderTest : ComponentBuilderTest<ToggleButton, ToggleButtonBuilder>() {

    override lateinit var target: ToggleButtonBuilder

    @Before
    override fun setUp() {
        target = ToggleButtonBuilder.newBuilder()
    }

    @Test
    override fun shouldProperlyApplyTitle() {
        target.withDecorations(box(title = TITLE_FOO))
    }
}

