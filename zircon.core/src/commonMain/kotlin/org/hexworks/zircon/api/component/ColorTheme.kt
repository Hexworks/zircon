package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor

interface ColorTheme {
    val isDefault: Boolean
        get() = this == ColorThemes.default()
    val primaryForegroundColor: TileColor
    val secondaryForegroundColor: TileColor
    val primaryBackgroundColor: TileColor
    val secondaryBackgroundColor: TileColor
    val accentColor: TileColor

    /**
     * Creates a [ComponentStyleSet] which is intended to be used as a default
     * for interactable components (eg: [Button]s, [ToggleButton]s and so on).
     */
    fun toInteractableStyle(): ComponentStyleSet = ComponentStyleSetBuilder.newBuilder()
            .withDefaultStyle(StyleSetBuilder.newBuilder()
                    .withForegroundColor(accentColor)
                    .withBackgroundColor(TileColor.transparent())
                    .build())
            .withMouseOverStyle(StyleSetBuilder.newBuilder()
                    .withForegroundColor(secondaryForegroundColor)
                    .withBackgroundColor(accentColor)
                    .build())
            .withFocusedStyle(StyleSetBuilder.newBuilder()
                    .withForegroundColor(primaryBackgroundColor)
                    .withBackgroundColor(accentColor)
                    .build())
            .withActiveStyle(StyleSetBuilder.newBuilder()
                    .withForegroundColor(primaryForegroundColor)
                    .withBackgroundColor(accentColor)
                    .build())
            .withDisabledStyle(StyleSetBuilder.newBuilder()
                    .withForegroundColor(accentColor.desaturate(.85).darkenByPercent(.1))
                    .withBackgroundColor(TileColor.transparent())
                    .build())
            .build()

    /**
     * Creates a [ComponentStyleSet] which is intended to be used as a default
     * for containers (eg: [Panel]s, [HBox]es, [VBox]es.
     */
    fun toContainerStyle(): ComponentStyleSet = ComponentStyleSetBuilder.newBuilder()
            .withDefaultStyle(StyleSetBuilder.newBuilder()
                    .withForegroundColor(secondaryForegroundColor)
                    .withBackgroundColor(primaryBackgroundColor)
                    .build())
            .withDisabledStyle(StyleSetBuilder.newBuilder()
                    .withForegroundColor(secondaryForegroundColor.desaturate(.8))
                    .withBackgroundColor(primaryBackgroundColor.desaturate(.8))
                    .build())
            .build()

    /**
     * Creates a [ComponentStyleSet] which is intended to be used as a default
     * for emphasized content (eg: [Header]s).
     */
    fun toPrimaryContentStyle(): ComponentStyleSet = ComponentStyleSetBuilder.newBuilder()
            .withDefaultStyle(StyleSetBuilder.newBuilder()
                    .withForegroundColor(primaryForegroundColor)
                    .withBackgroundColor(TileColor.transparent())
                    .build())
            .withDisabledStyle(StyleSetBuilder.newBuilder()
                    .withForegroundColor(primaryForegroundColor.desaturate(.8))
                    .withBackgroundColor(TileColor.transparent())
                    .build())
            .build()

    /**
     * Creates a [ComponentStyleSet] which is intended to be used as a default
     * for non-emphasized content (eg: [Label]s, [Paragraph]s, and so on).
     */
    fun toSecondaryContentStyle(): ComponentStyleSet = ComponentStyleSetBuilder.newBuilder()
            .withDefaultStyle(StyleSetBuilder.newBuilder()
                    .withForegroundColor(secondaryForegroundColor)
                    .withBackgroundColor(TileColor.transparent())
                    .build())
            .withDisabledStyle(StyleSetBuilder.newBuilder()
                    .withForegroundColor(secondaryForegroundColor.desaturate(.7).darkenByPercent(.5))
                    .withBackgroundColor(TileColor.transparent())
                    .build())
            .build()
}
