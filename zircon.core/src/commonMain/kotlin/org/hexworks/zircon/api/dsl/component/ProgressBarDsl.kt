package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.ProgressBarBuilder
import org.hexworks.zircon.api.component.ProgressBar

fun progressBar(init: ProgressBarBuilder.() -> Unit): ProgressBar =
    ProgressBarBuilder().apply(init).build()
