package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.LogArea
import org.junit.Before

class LogAreaBuilderTest : ComponentBuilderTest<LogArea, LogAreaBuilder>() {

    override lateinit var target: LogAreaBuilder

    @Before
    override fun setUp() {
        target = LogAreaBuilder.newBuilder()
    }
}

