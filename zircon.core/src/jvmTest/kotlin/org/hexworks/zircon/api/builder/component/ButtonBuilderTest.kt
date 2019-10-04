package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Button
import org.junit.Before

class ButtonBuilderTest : ComponentBuilderTest<Button, ButtonBuilder>() {

    override lateinit var target: ButtonBuilder

    @Before
    override fun setUp() {
        target = ButtonBuilder.newBuilder()
    }

}

