package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Label
import kotlin.test.BeforeTest

class LabelBuilderTest : JvmComponentBuilderTest<Label>() {

    override lateinit var target: LabelBuilder

    @BeforeTest
    override fun setUp() {
        target = LabelBuilder()
    }

}
