package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Header
import kotlin.test.BeforeTest

class HeaderBuilderTest : JvmComponentBuilderTest<Header>() {

    override lateinit var target: HeaderBuilder

    @BeforeTest
    override fun setUp() {
        target = HeaderBuilder()
    }
}
