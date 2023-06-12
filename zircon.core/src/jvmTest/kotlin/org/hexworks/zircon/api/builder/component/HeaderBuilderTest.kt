package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Header
import org.junit.Before

class HeaderBuilderTest : JvmComponentBuilderTest<Header>() {

    override lateinit var target: HeaderBuilder

    @Before
    override fun setUp() {
        target = HeaderBuilder()
    }
}

