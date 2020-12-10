package org.hexworks.zircon.examples.playground

data class TableField<M : Any, V : Any?>(
    val name: String,
    val width: Int,
    val accessor: (M) -> V
) {
    fun getValue(model: M): V? = accessor(model)
}
