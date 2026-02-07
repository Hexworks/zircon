package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Button
import kotlin.test.BeforeTest

class ButtonBuilderTest : JvmComponentBuilderTest<Button>() {

    override lateinit var target: ButtonBuilder

    @BeforeTest
    override fun setUp() {
        target = ButtonBuilder()
    }

}
