package org.hexworks.zircon.internal.component.theme.neobrutalism

import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.component.theme.ComponentPalette
import org.hexworks.zircon.api.component.theme.componentPalette

/**
 * HyperUI's Neobrutalism colors (Tailwind names in the comments) as [ComponentPalette]s. Ink
 * is every `ON_*` and the border, paper is every surface; the yellow is the primary (the
 * double border gap, the search button) and its paler shade the accent (hover, the selected
 * tab); the dark variant swaps ink and paper and uses amber (with the paper, not the ink, as
 * the text on the primary: the only pair of the two that reads).
 */
internal object NeobrutalismPalettes {

    val light: ComponentPalette = componentPalette {
        name = "Neobrutalism (light)"
        isDark = false
        val ink = Color.fromString("#000000")
        val paper = Color.fromString("#ffffff")
        background = paper
        onBackground = ink
        surface = paper
        onSurface = ink
        surfaceRaised = paper
        onSurfaceRaised = ink
        surfaceSunken = paper
        textMuted = Color.fromString("#6b7280") // gray-500: gray-400 is 2.5:1 on white
        textDisabled = Color.fromString("#9ca3af") // gray-400
        border = ink
        borderFocused = ink
        primary = Color.fromString("#fde047") // yellow-300
        onPrimary = ink
        secondary = paper
        onSecondary = ink
        accent = Color.fromString("#fef08a") // yellow-200
        onAccent = ink
        selection = Color.fromString("#fef08a")
        onSelection = ink
        info = Color.fromString("#2563eb") // blue-600
        onInfo = paper
        success = Color.fromString("#16a34a") // green-600
        onSuccess = paper
        warning = Color.fromString("#d97706") // amber-600
        onWarning = paper
        error = Color.fromString("#dc2626") // red-600
        onError = paper
    }

    val dark: ComponentPalette = componentPalette {
        name = "Neobrutalism (dark)"
        isDark = true
        val ink = Color.fromString("#ffffff")
        val paper = Color.fromString("#111827") // gray-900
        background = paper
        onBackground = ink
        surface = paper
        onSurface = ink
        surfaceRaised = paper
        onSurfaceRaised = ink
        surfaceSunken = paper
        textMuted = Color.fromString("#6b7280") // gray-500
        textDisabled = Color.fromString("#6b7280")
        border = ink
        borderFocused = ink
        primary = Color.fromString("#ca8a04") // yellow-600
        onPrimary = paper // the white ink is 2.9:1 on yellow-600, the dark paper 7.6:1
        secondary = paper
        onSecondary = ink
        accent = Color.fromString("#a16207") // yellow-700
        onAccent = ink
        selection = Color.fromString("#a16207")
        onSelection = ink
        info = Color.fromString("#60a5fa") // blue-400
        onInfo = paper
        success = Color.fromString("#86efac") // green-300
        onSuccess = paper
        warning = Color.fromString("#fbbf24") // amber-400
        onWarning = paper
        error = Color.fromString("#fca5a5") // red-300
        onError = paper
    }
}
