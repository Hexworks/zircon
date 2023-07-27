package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.LogArea
import org.junit.Before

class LogAreaBuilderTest : JvmComponentBuilderTest<LogArea>() {

    override lateinit var target: LogAreaBuilder

    @Before
    override fun setUp() {
        target = LogAreaBuilder()
    }
}

