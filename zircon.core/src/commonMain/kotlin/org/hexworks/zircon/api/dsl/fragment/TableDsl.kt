package org.hexworks.zircon.api.dsl.fragment

import org.hexworks.zircon.api.Beta
import org.hexworks.zircon.api.builder.fragment.TableBuilder
import org.hexworks.zircon.api.builder.fragment.TableColumnBuilder
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.dsl.AnyContainerBuilder
import org.hexworks.zircon.api.dsl.component.buildFragmentFor
import org.hexworks.zircon.api.fragment.Table
import org.hexworks.zircon.api.fragment.TableColumn

/**
 * Creates a new [Table] using the fragment builder DSL and returns it.
 */
@Beta
fun <T : Any> buildTable(
    init: TableBuilder<T>.() -> Unit
): Table<T> = TableBuilder.newBuilder<T>().apply(init).build()

/**
 * Creates a new [Table] using the fragment builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [Table].
 */
@Beta
fun <T : Any> AnyContainerBuilder.table(
    init: TableBuilder<T>.() -> Unit
): Table<T> = buildFragmentFor(this, TableBuilder.newBuilder(), init)
