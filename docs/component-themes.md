# Component Themes

Currently we have `ColorTheme`s in Zircon which is a very limited theming feature: it has fixed 5 colors, and nothing else.
Component themes let a theme set, for every `Component`:

- the `ComponentRenderingStrategy` (content renderer, decorations, post processors), so the look and feel changes, not
  just the colors
- the `ComponentStyleSet` (colors per `ComponentState`), derived from the theme's own palette

The decisions below come from the grill on 2026-11-14. The first theme is Neobrutalism, see
[neo-brutalism.md](neo-brutalism.md).

## Model

```
ComponentTheme  (name, mode, entryFor(role): ThemeEntry?, componentFactory)
   └── ThemeEntry  (componentRenderer, decorationRenderers, postProcessors, componentStyleSet)
ComponentRole   enum: BUTTON, BUTTON_PRIMARY, ..., ALERT_PANEL, ALERT_TEXT, ...
ThemeMode       enum: LIGHT, DARK, SYSTEM
```

- **Themes are data.** A theme is a map from `ComponentRole` to a `ThemeEntry` plus a palette. There is no per-theme
  builder factory implementation; one generic `ComponentFactory` serves every theme.
- **Roles, not types.** Every component carries a `ComponentRole`. Builders default it to the component kind (`BUTTON`,
  `LABEL`, `PANEL`...). Composite fragments set finer roles on their parts (`ALERT_TEXT`, `BADGE_DISMISS`...), so the
  theme can style the label inside an alert differently from a plain label. Roles are **semantic** so they port between
  themes: `BUTTON`, `BUTTON_PRIMARY`, `BUTTON_SECONDARY`, `BUTTON_LINK` rather than "underlined" or "double border".
  The enum is closed; a custom component falls back to the role its builder gives it.
- **Theme lives on the `Screen`**, as `componentTheme` (a `Property<ComponentTheme?>`), and propagates to every attached
  component the same way `ColorTheme` does: `DefaultContainer` binds the child's `componentThemeProperty` to the
  parent's on attach, honoring `bindingAction`. `ZirconDefaults.componentTheme` is the process-wide default (`null`
  means the legacy, un-themed path). `TileGrid` is untouched.
- **ComponentTheme wins over ColorTheme.** When a component has a component theme (own or propagated) its style set
  comes from the theme and `ColorTheme` changes are ignored. Without a component theme nothing changes, so the two
  co-exist until `ColorTheme` is sunset.
- **Switching keeps layout.** A component's outer size is fixed when it is built. On a theme switch the rendering
  strategy is replaced and `contentOffset` / `contentSize` are recomputed from the new decorations (clamped at zero);
  a container moves its children along with its content offset. Nothing else moves. Decorations that would leave no
  content cell at all (a 3-row frame on a 1-row button) are skipped for that component; its renderer and colors still
  apply. Size components for the heaviest theme you plan to use, or build them through the factory.
- **No entry, no change.** A role the theme has no entry for keeps the look it was built with and follows the
  `ColorTheme` as before, so layout-only containers (`ComponentRole.NONE`) and custom components stay as they are.
- **Explicit beats theme.** Setting `componentRenderer`, `decorationRenderers` or `componentStyleSet` on a builder pins
  that aspect: the theme fills in the aspects you did not touch and leaves pinned ones alone on every switch.
- **Variants are theme-specific.** Core knows `name` and `mode` only. Each theme's DSL adds its own knobs
  (`neobrutalism { mode = DARK }`, later `solarized { mode = DARK; accent = CYAN }`). A generic `Variant` abstraction
  waits until a second theme needs one.
- **`ThemeMode.SYSTEM`** is resolved by `SystemThemeDetector`, an `expect`/`actual` utility in core: JVM asks the OS
  (macOS `defaults read -g AppleInterfaceStyle`, Linux `gsettings`/portal, Windows registry), wasmJs asks
  `prefers-color-scheme`, native targets do the same per OS. Unknown resolves to `DARK`.

## Usage

```kt
val screen = tileGrid.toScreen()
screen.componentTheme = ComponentThemes.neobrutalism { mode = ThemeMode.SYSTEM }

val components = screen.componentFactory

val button = components.button {
    text = "Save"
    role = ComponentRole.BUTTON_PRIMARY
}
val alert = components.alert {
    kind = AlertKind.SUCCESS
    text = "Changes saved"
}

// later
screen.componentTheme = ComponentThemes.neobrutalism { mode = ThemeMode.LIGHT }
```

`componentFactory` reads the screen's current theme, runs your block, then applies the theme's entry for the builder's
final role (so `role = BUTTON_PRIMARY` inside the block counts and anything you set explicitly is pinned), and only then
computes the size, so it includes the theme's decorations. Children built inside a themed builder's block (`button { }`
inside `components.vBox { }`) inherit the theme the same way, and so do nested fragments. Components built with the plain
DSL outside any themed builder get themed when they are attached to a themed container, but their size was computed
for the default decorations, so prefer the factory when sizes matter.

Neobrutalism needs a tileset with the heavy box-drawing glyphs and the Unicode block elements (`┏ ━ ▌ ▀ ▘ ▎ ▔ ▂`).
CP437 sheets have only `▌ ▀ █`, so use a TrueType or Nerd Font tileset.

## Internals

- `DefaultComponent.renderer` becomes mutable; `contentOffset`, `contentSize` and `contentBoundsProperty` are derived
  from the current strategy. Removing the theme (or switching to one without an entry) restores the strategy the
  component was built with.
- Propagation is not a plain binding: a parent passes its theme to a child on attach and on change, but a parent
  *without* a theme does not clear a child's theme that was set explicitly (`componentTheme = x` on the builder or
  the component). That is how a themed subtree lives inside an unthemed screen (see the examples app).
- `CommonComponentProperties` tracks which of renderer / decorations / style set the user set explicitly (the pins).
- On `componentThemeProperty` change the component looks up `theme.entryFor(role)`; a missing entry leaves the component
  as it is.
- `ComponentFactory` is one class in core. Composite fragments (`Alert`, `Badge`, `Card`, `Window`, `InputField`,
  `LabeledProgressBar`, `TextAreaField`) are generic `Fragment`s in core built from roles; themes only supply looks.
- Neobrutalism lives in core: `org.hexworks.zircon.api.component.theme` (interfaces, `ComponentThemes`) and
  `org.hexworks.zircon.internal.component.theme.neobrutalism` (renderers).

## Component Refactoring

Role based dispatch needs none of the refactors below, so they ship separately. This session adds the single-line
`Input` component (needed by the Input fragment); the rest are tracked in
[component-refactoring-handover.md](component-refactoring-handover.md):

1. `CheckBox`, `RadioButton`, `ToggleButton` → one `BinarySelector`; `RadioButtonGroup` → `ExclusiveSelectionGroup`;
   `Selectable` gets a `key`
2. `HBox` / `VBox` dedupe
3. `TextArea` and `ScrollBar` fixes

## Test Implementation

The first component theme is [Neobrutalism](https://hyperui.dev/components/neobrutalism/). Mockups and the glyph mapping
are in [neo-brutalism.md](neo-brutalism.md).

- Accordion: out of scope
- Select: out of scope
- Alert: `Fragment` with a `Panel`, an `Icon` and a `Label`
- Badge: `HBox` with a `Label` and optional `Icon` (left) and `Button` (right). Pressing the `Button` detaches the Badge.
- Button: `Button` with roles `BUTTON` (pressed), `BUTTON_PRIMARY` (double border), `BUTTON_SECONDARY` (base),
  `BUTTON_LINK` (underlined). Pressed-with-inset is dropped.
- Card: `VBox` with a `HBox` (`Icon` + `Label`), a `Header` and a `Paragraph`. Dashed variant skipped. Retro window is
  its own `Window` fragment.
- Checkbox: `VBox` with a `CheckBox` and an optional `Paragraph` below. Grouped variant out of scope.
- Input: `VBox` with a `Label` and a `HBox` holding an `Input` plus optional `Icon` or `Button`
- Progressbar: `VBox` with a `ProgressBar` and an optional `HBox` of two `Label`s above
- Tabs: `HorizontalTabBar` / `VerticalTabBar` with theme renderers
- Textarea: `TextArea` or `VBox` with optional `Label`, the `TextArea` and an optional `HBox` of `Button`s
