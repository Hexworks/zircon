package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Panel
import org.junit.Before

class PanelBuilderTest : ComponentBuilderTest<Panel, PanelBuilder>() {

    override lateinit var target: PanelBuilder

    @Before
    override fun setUp() {
        target = PanelBuilder.newBuilder()
    }
}
