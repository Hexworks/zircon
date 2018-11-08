package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.RadioButtonGroup
import org.junit.Before

class RadioButtonGroupBuilderTest : ComponentBuilderTest<RadioButtonGroup, RadioButtonGroupBuilder>() {

    override lateinit var target: RadioButtonGroupBuilder

    @Before
    override fun setUp() {
        target = RadioButtonGroupBuilder.newBuilder()
    }
}
