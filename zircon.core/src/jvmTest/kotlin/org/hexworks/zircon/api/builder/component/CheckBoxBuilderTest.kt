package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.CheckBox
import org.junit.Before

class CheckBoxBuilderTest : ComponentBuilderTest<CheckBox, CheckBoxBuilder>() {

    override lateinit var target: CheckBoxBuilder

    @Before
    override fun setUp() {
        target = CheckBoxBuilder.newBuilder()
    }
}

