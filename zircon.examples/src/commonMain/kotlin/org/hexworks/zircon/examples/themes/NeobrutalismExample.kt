package org.hexworks.zircon.examples.themes

import org.hexworks.zircon.api.ComponentThemes
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.hBox
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.builder.fragment.alert
import org.hexworks.zircon.api.builder.fragment.badge
import org.hexworks.zircon.api.builder.fragment.card
import org.hexworks.zircon.api.builder.fragment.checkBoxField
import org.hexworks.zircon.api.builder.fragment.horizontalTabBar
import org.hexworks.zircon.api.builder.fragment.inputField
import org.hexworks.zircon.api.builder.fragment.progressField
import org.hexworks.zircon.api.builder.fragment.textAreaField
import org.hexworks.zircon.api.builder.fragment.window
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.component.theme.Part
import org.hexworks.zircon.api.component.theme.Variant
import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.component.theme.ThemeMode
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.fragment.AlertKind
import org.hexworks.zircon.api.fragment.BadgeStatus
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleContext
import org.hexworks.zircon.examples.ExampleView
import org.hexworks.zircon.api.Palettes

/**
 * Every Neobrutalism component, built through the theme's component factory. The shape is
 * pinned to this subtree; the colors are the app's palette (the header selector), so any
 * bundled palette can be tried on it. The Light / Dark buttons switch the app to HyperUI's
 * Neobrutalism palette of that mode; System is the header selector's `System` with the
 * Neobrutalism pair as the family: the host's own colors where it has them, else light or
 * dark after the desktop setting, until another palette is picked.
 */
public val neobrutalismExample: Example = Example("Neobrutalism") { context ->
    val theme = ComponentThemes.neobrutalism()
    val root: VBox = vBox {
        preferredSize = context.contentSize
        componentTheme = theme
    }
    root.addComponent(buildContent(context, theme) { mode ->
        if (mode == ThemeMode.SYSTEM) {
            context.followSystemPalette { Palettes.neobrutalism(it) }
        } else {
            context.palette.value = Palettes.neobrutalism(mode)
        }
    })
    ExampleView(root)
}

private fun buildContent(context: ExampleContext, theme: ComponentTheme, switchTo: (ThemeMode) -> Unit): Component {
    val components = theme.componentFactory
    val width = context.contentSize.width
    val height = context.contentSize.height
    val column = (width - 2) / 3

    val headerRow = components.hBox {
        preferredContentSize = Size(width, 4)
        spacing = 1
        ThemeMode.entries.forEach { mode ->
            button {
                +mode.name.lowercase().replaceFirstChar { it.uppercase() }
                variant = Variant.SECONDARY
                processComponentEvents(ComponentEventType.ACTIVATED) { switchTo(mode) }
            }
        }
    }
    val left = components.vBox {
        preferredContentSize = Size(column, height - 5)
        alert {
            this.width = column
            kind = AlertKind.SUCCESS
            +"Saved. All good."
        }
        hBox {
            preferredContentSize = Size(column, 4)
            spacing = 1
            badge { +"Ok" }
            badge { +"Up"; status = BadgeStatus.SUCCESS }
            badge { +"Hi"; dismissible = true }
        }
        hBox {
            preferredContentSize = Size(column, 4)
            spacing = 1
            button { +"Click" }
            button { +"Save"; variant = Variant.PRIMARY }
            button { +"More"; variant = Variant.LINK }
        }
        checkBoxField {
            this.width = column
            label = "Option 1"
            description = "Lorem ipsum dolor sit amet."
        }
        horizontalTabBar {
            size = Size(column, 7)
            tab { key = "profile"; label = "Profile"; content = tabContent(components, column, "Profile tab") }
            tab { key = "account"; label = "Account"; content = tabContent(components, column, "Account tab") }
        }
    }
    val middle = components.vBox {
        preferredContentSize = Size(column, height - 5)
        inputField {
            this.width = column
            label = "Email"
            button = "SEARCH"
        }
        progressField {
            this.width = column
            label = "Updating"
            progress = 75.0
        }
        textAreaField {
            this.width = column
            rows = 2
            label = "Notes"
            action("Clear")
            action("Save", Variant.PRIMARY)
        }
    }
    val right = components.vBox {
        preferredContentSize = Size(column, height - 5)
        card {
            this.width = column
            icon = '▦'
            subtitle = "APRIL 1, 2025"
            title = "How I built my first website"
            body = "Lorem ipsum dolor sit amet consectetur adipisicing elit."
        }
        window {
            this.width = column
            title = "SYSTEM MESSAGE."
            header = "Retro Window"
            body = "Lorem ipsum dolor sit amet consectetur."
        }
    }
    return components.vBox {
        preferredContentSize = Size(width, height)
        spacing = 1
        childrenToAdd = listOf(headerRow, components.hBox {
            preferredContentSize = Size(width, height - 5)
            spacing = 1
            childrenToAdd = listOf(left, middle, right)
        })
    }
}

private fun tabContent(components: org.hexworks.zircon.api.component.theme.ComponentFactory, width: Int, text: String): Component =
    components.panel {
        role = ComponentRole.TAB_BAR
        part = Part.CONTENT
        preferredSize = Size(width, 4)
        label { +text }
    }
