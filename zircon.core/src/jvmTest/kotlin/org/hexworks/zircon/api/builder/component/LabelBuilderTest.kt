package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Label
import org.junit.Before

class LabelBuilderTest : JvmComponentBuilderTest<Label>() {

    override lateinit var target: LabelBuilder

    @Before
    override fun setUp() {
        target = LabelBuilder()
    }

}

