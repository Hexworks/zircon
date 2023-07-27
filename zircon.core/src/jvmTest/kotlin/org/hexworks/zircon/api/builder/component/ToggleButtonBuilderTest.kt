package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.component.ToggleButton
import org.hexworks.zircon.api.component.builder.base.decorations
import org.junit.Before
import org.junit.Test

class ToggleButtonBuilderTest : JvmComponentBuilderTest<ToggleButton>() {

    override lateinit var target: ToggleButtonBuilder

    @Before
    override fun setUp() {
        target = ToggleButtonBuilder()
    }

    @Test
    override fun shouldProperlyApplyTitle() {
        target.decorations {
            +box(title = TITLE_FOO)
        }
    }
}

