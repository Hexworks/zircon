package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Header
import org.junit.Before

class HeaderBuilderTest : ComponentBuilderTest<Header, HeaderBuilder>() {

    override lateinit var target: HeaderBuilder

    @Before
    override fun setUp() {
        target = HeaderBuilder.newBuilder()
    }
}

