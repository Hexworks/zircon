package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.LogArea
import kotlin.test.BeforeTest

class LogAreaBuilderTest : JvmComponentBuilderTest<LogArea>() {

    override lateinit var target: LogAreaBuilder

    @BeforeTest
    override fun setUp() {
        target = LogAreaBuilder()
    }
}
