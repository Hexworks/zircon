package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.CheckBox
import kotlin.test.BeforeTest

class CheckBoxBuilderTest : JvmComponentBuilderTest<CheckBox>() {

    override lateinit var target: CheckBoxBuilder

    @BeforeTest
    override fun setUp() {
        target = CheckBoxBuilder()
    }
}
