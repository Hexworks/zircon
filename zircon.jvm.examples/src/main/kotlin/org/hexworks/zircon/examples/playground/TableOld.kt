package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.data.Size

class TableOld<M : Any>(
    fields: List<ModelField<M>>,
    models: List<M>
) : Fragment {

    private val size = Size.create(
        width = fields.map(TableField<M, out Any?>::width).fold(0, Int::plus),
        height = models.size + 1
    )

    override val root = Components.hbox()
        .withSize(size)
        .build()

    init {
        fields.forEach { field ->
            val column = Components.vbox()
                .withSize(field.width, size.height)
                .build()
            column.addComponent(
                Components.header()
                    .withText(field.name)
                    .withSize(field.width, 1)
            )
            models.forEach { model ->
                column.addComponent(
                    Components.label()
                        .withText(field.accessor(model).toString())
                        .withSize(field.width, 1)
                )
            }
            root.addComponent(column)
        }
    }

}

private typealias ModelField<M> = TableField<M, out Any?>
