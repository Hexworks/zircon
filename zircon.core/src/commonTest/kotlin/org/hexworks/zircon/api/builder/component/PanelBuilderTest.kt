package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Panel
import kotlin.test.BeforeTest

class PanelBuilderTest : JvmComponentBuilderTest<Panel>() {

    override lateinit var target: PanelBuilder

    @BeforeTest
    override fun setUp() {
        target = PanelBuilder()
    }
}
