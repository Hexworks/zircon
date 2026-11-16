package org.hexworks.zircon.api.component.theme

/**
 * What a [ComponentTheme] maps to a [ThemeEntry]: a [ComponentRole] with a [Variant] and a
 * [Part].
 */
public data class ThemeKey(
    val role: ComponentRole,
    val variant: Variant = Variant.DEFAULT,
    val part: Part = Part.NONE,
) {
    /**
     * The keys a theme is asked for, in order, when it has no entry for this one: the
     * default variant, then the component's own [kind] (what the builder made, `LABEL` for a
     * card title) with this variant, then the kind's default. A part never falls back to its
     * composite's own entry, which would give a title the card's frame. [ComponentRole.NONE]
     * has no fallbacks at all: it means "never themed", whatever the kind.
     */
    public fun fallbacks(kind: ComponentRole = role): List<ThemeKey> = when (role) {
        ComponentRole.NONE -> emptyList()
        else -> listOf(
            this,
            copy(variant = Variant.DEFAULT),
            ThemeKey(kind, variant),
            ThemeKey(kind),
        ).distinct()
    }

    override fun toString(): String = buildString {
        append(role)
        if (variant != Variant.DEFAULT) append('/').append(variant)
        if (part != Part.NONE) append('#').append(part)
    }
}
