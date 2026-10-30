package org.hexworks.zircon.examples.dialogs

import org.hexworks.zircon.api.ComponentAlignments.alignmentWithin
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.modal
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.extensions.withX
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView
import org.hexworks.zircon.internal.component.modal.EmptyModalResult

/** A centred dialog with its own theme, opened on a button press and closed by its button. */
public val dialogExample: Example = Example("Dialog") { context ->
    val screen = context.screen
    val root = vBox { preferredSize = context.contentSize; decorations { +box(title = "Dialog") } }
    root.addComponent(button { +"Open dialog" }.apply {
        onActivated {
            val panel = panel {
                preferredSize = Size(25, 16)
                decorations { +box(title = "Modal") }
            }
            panel.addComponent(panel {
                preferredSize = Size(23, 13)
                colorTheme = screen.theme
            })
            val modal = modal {
                centeredDialog = true
                preferredSize = screen.size
                // A modal has no parent, so it needs its own theme and tileset.
                colorTheme = screen.theme
                tileset = screen.tileset
                contentComponent = panel
            }
            panel.addComponent(button {
                +"Close"
                alignment = alignmentWithin(panel, ComponentAlignment.BOTTOM_CENTER)
            }.apply { onActivated { modal.close(EmptyModalResult) } })
            screen.openModal(modal)
        }
    })
    ExampleView(root)
}

/** Modals that open more modals: each level darkens what is below; `C` closes the top one. */
public val nestedModalsExample: Example = Example("Nested modals") { context ->
    val screen = context.screen
    val root = vBox { preferredSize = context.contentSize; decorations { +box(title = "Nested modals") } }
    root.addComponent(openAnotherButton(screen, Position.ZERO, 1))
    ExampleView(root)
}

private fun openModal(screen: Screen, level: Int) {
    val modalPanel = panel {
        preferredSize = Size(30, 20)
        decorations {
            +box(title = "Modal level: $level")
            +shadow()
        }
    }
    val modal = modal {
        contentComponent = modalPanel
        preferredSize = screen.size
        colorTheme = screen.theme
        tileset = screen.tileset
        darkenPercent = .1
        centeredDialog = true
    }.apply {
        handleKeyboardEvents(KeyboardEventType.KEY_PRESSED) { event, _ ->
            if (event.code == KeyCode.KEY_C) {
                close(EmptyModalResult)
                Processed
            } else Pass
        }
    }
    val closeButton = button {
        +"Close"
        alignment = alignmentWithin(modalPanel, ComponentAlignment.BOTTOM_RIGHT)
    }.apply { onActivated { modal.close(EmptyModalResult) } }
    modalPanel.addComponent(openAnotherButton(screen, closeButton.position.withX(0), level + 1))
    modalPanel.addComponent(closeButton)
    screen.openModal(modal)
}

private fun openAnotherButton(screen: Screen, position: Position, level: Int) = button {
    +"Open another"
    this.position = position
}.apply { onActivated { openModal(screen, level) } }

public val dialogExamples: List<Example> = listOf(dialogExample, nestedModalsExample)
