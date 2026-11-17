# Component Palettes

Follow-up to [component-themes.md](component-themes.md). Decided in the grill on 2026-11-16, after the research in
[research/semantic-component-palettes.md](research/semantic-component-palettes.md).

## Why

A `ComponentTheme` today bundles *shape* (renderers, decorations, post processors) with *color* (`ComponentStyleSet`
per role, from a theme-private palette). That forces one copy of Neobrutalism per color scheme. Every system the
research looked at (lazygit, Textual, DaisyUI, shadcn, Kanagawa, Catppuccin) separates three layers: a **named palette**
(Catppuccin `mauve`), a **semantic layer** (`primary`, `surface`, `error`) and **consumers** that read semantic keys
only. Shape lives apart from both (lazygit `gui.border`, Textual widget CSS). mc, which keeps glyphs and colors in one
skin file, needs N x M skin copies; that is the failure we avoid.

## Model

```
Screen
  ├── componentTheme:   ComponentTheme    (never null; ComponentThemes.default() is the base)
  └── componentPalette: ComponentPalette  (never null; default = ZirconDefaults.theme.toComponentPalette())

ComponentTheme   name, entryFor(role, variant, part): ThemeEntry   (falls back to the default theme)
ThemeEntry       componentRenderer, decorationRenderers, postProcessors, styles: (ComponentPalette) -> ComponentStyleSet
ComponentPalette isDark, [PaletteKey]: Color, overrides: Map<StyleOverrideKey, StyleSet>, withPrimary(...)
PaletteKey       enum, 27 keys (below)
ComponentRole    enum: the widget or composite kind      (BUTTON, PANEL, ALERT, TAB_BAR ...)
Variant          enum: DEFAULT, PRIMARY, SECONDARY, LINK, INFO, SUCCESS, WARNING, ERROR
Part             enum: NONE, TEXT, ICON, TITLE, BODY, DISMISS, DOT, LABEL, ROW, INPUT, BUTTON, VALUE, HEADER,
                       META, SUBTITLE, TITLE_BAR, CONTROLS, DESCRIPTION, ACTIONS, CONTENT, SCROLL
```

### Decisions

- **Theme = shape, palette = color.** A `ThemeEntry` no longer holds a `ComponentStyleSet`; it holds
  `styles: (ComponentPalette) -> ComponentStyleSet`. The theme owns *how* a component's five `ComponentState` colors
  derive from the palette (a Neobrutalist hover is a hard swap, a flat hover is a 15% blend); the palette owns *which*
  colors. Core ships reusable derivations (`surfaceStyles`, `textStyles`, `interactiveStyles(variant)`, `fieldStyles`,
  `selectionStyles`) that the default theme and Neobrutalism share; a theme may hand-write its own. Blends use the
  existing `Color` extensions (`interpolateTo`, `tint`, `shade`, `tone`, `desaturate`, `lightenByPercent`,
  `darkenByPercent`); no new color primitive.
- **Palette may override.** `ComponentPalette.overrides[(role, variant, part, state)]` is a sparse map applied after the
  theme's derivation, the way Textual themes override a handful of generated variables. Palette authors fill 27 keys plus
  taste; they never author five states per key.
- **A theme is always active.** The stock renderers and decorations move into `ComponentThemes.default()`, the base
  every theme falls back to, so `componentTheme` is never null and the `hasThemeEntry` / legacy branches in
  `DefaultComponent` go away. Builders take their defaults from the default theme's entries (single source).
- **A palette is always active.** `componentPalette` defaults to `ZirconDefaults.current.theme.toComponentPalette()`,
  so apps that only set a `ColorTheme` keep their colors through the new pipeline. `ColorTheme` becomes a deprecated
  input format (`@Deprecated`, removed after 2.0); `convertColorTheme` per component is replaced by the default theme's
  derivations. The 77 built-in color themes keep working through the adapter.
- **Theme and palette switch independently.** Both are screen properties propagated parent -> child like the theme is
  today (`ComponentPaletteOverride` behavior, `inheritComponentPalette`). A component recomputes its style set when either
  changes. Explicit `componentStyleSet` on a builder still pins.
- **Light/dark is a palette property.** `ComponentTheme.mode` goes away; `ComponentPalette.isDark` replaces it.
  `ThemeMode` and `SystemThemeDetector` stay and resolve on the palette side:
  `Palettes.catppuccin(mode = SYSTEM)` picks latte or mocha, `tokyoNight` day or night, `kanagawa` lotus or wave,
  `neobrutalism` its light or dark palette. `NeobrutalismBuilder` loses `mode` and `palette`.
- **Roles split into three closed enums.** The flat `ComponentRole` mixed kind (`BUTTON`), visual variant
  (`BUTTON_PRIMARY`), composite part (`ALERT_TEXT`) and state (`TAB_SELECTED`). It becomes `role` (kind) + `variant` +
  `part`, all closed core enums so they port between themes. `variant` is what picks palette keys
  (`WARNING -> warning / onWarning`). State stays out: a selected tab is `role = TAB` and the renderer reads
  `isSelected` (a selected tab is a `Label`, not `Selectable`, so "not selectable" reads as selected), colored from
  `selection / onSelection`; a vertical tab bar is `role = TAB_BAR` and the renderer reads the new
  `ComponentProperties.orientation` (`Orientation.HORIZONTAL / VERTICAL`, set by the vertical builders and the tab
  fragments). ARIA-style state flags and ARIA role names are deferred (ROADMAP).
- **Lookup fallback: default variant, then the component kind.** Every component also carries its `kind`, what the
  builder made (`LABEL` for a card title), fixed by the builder. `(role, variant, part)` -> `(role, DEFAULT, part)` ->
  `(kind, variant)` -> `(kind, DEFAULT)`, first in the theme, then the same chain in the default theme. A part never
  falls back to its composite's own entry (that would give the title the card's frame); it falls back to being a plain
  label. `ComponentRole.NONE` has no fallbacks: it means never themed, whatever the kind. The *variant* still reaches
  the derivation even when the entry is a fallback, so a `BUTTON PRIMARY` without its own entry gets the default
  button shape in primary colors. (The grill said "part, then variant, never role"; session C found the part -> NONE
  step hands children their container's decorations and replaced it with the kind step.)
- **Entries cascade aspect by aspect.** The entries found along the chain are merged (`ThemeEntry.over`): a `null`
  aspect in a more specific entry is taken from the next one, down to the default theme. So a theme writes only what
  it changes (Neobrutalism's `BUTTON PRIMARY` gives decorations and colors, the renderer comes from the default
  theme), and switching themes never leaks a renderer from the previous theme (session A; `null` used to mean "keep
  what the component has"). Consequence: an entry cannot say "keep the component's own decorations" any more; a
  fragment default that a theme should be able to drop is not expressible, which is why the stock tab strip lost its
  one-cell indent (below).
- **An explicit theme is pinned.** `componentTheme = x` on a builder or a component keeps `x` whatever the parent's
  theme is or becomes; everything else follows the parent. With a theme always present there is no "parent without
  a theme" to tell apart, so this replaced the old rule (a themed parent wins, a null parent does not clear).
- **Default derivations are the 1.x rules in palette terms.** `PaletteStyles`: containers are `BORDER` on `SURFACE`
  (`BORDER_FOCUSED` when focused), headers `ON_SURFACE`, labels `TEXT_MUTED`, interactive controls the emphasis
  color of their variant as text at rest and as the surface when hovered / focused / pressed, fields `ON_BACKGROUND`
  on `SURFACE_SUNKEN` with the selection pair while focused, the selected tab `ON_SELECTION` on `SELECTION`. Hover
  is still the 1.x `TEXT_MUTED` on the emphasis color; tune it for the bundled palettes in D if it reads badly.
- **The legacy `ColorTheme` still steers colors through the screen.** `screen.theme = x` switches the screen's
  palette to `x.toComponentPalette()` (only when `x` differs from the current theme, so a `View` docking with the
  default theme does not undo a palette set in `ZirconDefaults`); `ZirconDefaults.componentPalette` defaults to the
  adapted `ZirconDefaults.theme`; a builder's `colorTheme` presets (does not pin) the adapted palette. Per-component
  color themes do nothing else any more: `convertColorTheme` is gone.
- **Themes are singletons.** Shape has no knobs yet, so `ComponentThemes.neobrutalism()` and `default()` return
  objects; `NeobrutalismBuilder` is gone with `mode` and `palette`. A builder comes back when a theme grows a shape
  option.
- **`primary` is the palette author's default, user-overridable.** `palette.withPrimary(color)` /
  `withPrimary(color, onColor)` returns a copy; the Catppuccin DSL adds `accent = MAUVE` over its named accents.
- **Keys are an enum.** `PaletteKey` + `palette[key]`; builders expose named properties (`background = ...`) and the
  Catppuccin / Tokyo Night / Kanagawa builders expose their native names. Enum keys keep overrides, `withPrimary` and a
  future base16 import uniform.

### Palette keys (27)

| Key | Meaning |
|---|---|
| `BACKGROUND` / `ON_BACKGROUND` | the screen; body text |
| `SURFACE` / `ON_SURFACE` | panels, cards, dialogs: one step up |
| `SURFACE_RAISED` / `ON_SURFACE_RAISED` | popups, modals, tooltips: two steps up |
| `SURFACE_SUNKEN` | wells, input fields, status bars: one step down |
| `TEXT_MUTED`, `TEXT_DISABLED` | labels, hints; disabled text |
| `BORDER`, `BORDER_FOCUSED` | frame glyphs at rest / on the focused component (no separate focus ring: a tile has one fg and one bg) |
| `PRIMARY` / `ON_PRIMARY` | the brand / main action |
| `SECONDARY` / `ON_SECONDARY` | less emphasized action |
| `ACCENT` / `ON_ACCENT` | highlight, hover surfaces, badges |
| `SELECTION` / `ON_SELECTION` | selected row, selected tab, marked text |
| `INFO`, `SUCCESS`, `WARNING`, `ERROR` + `ON_*` | status |

Fixed three-step surface ladder (sunken / surface / raised) rather than computed shades: Catppuccin and Kanagawa map onto
it by hand (tables in the research doc, section 10).

### `ColorTheme` adapter

| ColorTheme | PaletteKey |
|---|---|
| `secondaryBackground` | `BACKGROUND`; `SURFACE_SUNKEN` = shaded (the 1.x themes paint the screen with it: gamebookers grey) |
| `primaryBackground` | `SURFACE`, `ON_PRIMARY`, `ON_ACCENT`, `ON_SECONDARY`; `SURFACE_RAISED` = tinted (the 1.x panel color: gamebookers blue) |
| `primaryForeground` | `ON_BACKGROUND`, `ON_SURFACE`, `ON_SURFACE_RAISED`, `ON_SELECTION` |
| `secondaryForeground` | `TEXT_MUTED`, `BORDER`; `TEXT_DISABLED` = toned |
| `accent` | `PRIMARY`, `ACCENT`, `BORDER_FOCUSED`; `SECONDARY` = desaturated; `SELECTION` = blended 40% toward `secondaryBackground` |
| (none) | `INFO` / `SUCCESS` / `WARNING` / `ERROR` = fixed blue / green / yellow / red, `ON_*` = `secondaryBackground` |

The grill table had `primaryBackground -> BACKGROUND`; session B swapped the two after reading `DefaultRootContainer`
(screen = `secondaryBackgroundColor`) and `toContainerStyle` (panels = `primaryBackgroundColor`), so the 77 themes
keep their screen and panel colors where they were.

### Role split mapping

| Today | After |
|---|---|
| `BUTTON_PRIMARY` / `_SECONDARY` / `_LINK` | `BUTTON` + variant `PRIMARY` / `SECONDARY` / `LINK` |
| `ALERT_INFO` / `_SUCCESS` / `_ERROR` | `ALERT` + variant; `ALERT_*_TEXT` -> `ALERT` + variant + part `TEXT` |
| `BADGE_TEXT`, `BADGE_DOT_SUCCESS`, `BADGE_DISMISS` | `BADGE` + part `TEXT` / `DOT` (variant `SUCCESS`) / `DISMISS` |
| `CARD_META` / `_ICON` / `_SUBTITLE` / `_TITLE` / `_BODY` | `CARD` + part |
| `WINDOW_TITLE_BAR` / `_TITLE` / `_CONTROLS` / `_HEADER` / `_BODY` | `WINDOW` + part |
| `CHECK_BOX_DESCRIPTION` | `CHECK_BOX_FIELD` + part `DESCRIPTION` |
| `INPUT_FIELD_LABEL` / `_ROW` / `_INPUT` / `_ICON` / `_BUTTON` | `INPUT_FIELD` + part |
| `PROGRESS_FIELD_HEADER` / `_LABEL` / `_VALUE` | `PROGRESS_FIELD` + part |
| `TEXT_AREA_FIELD_LABEL` / `_ACTIONS` | `TEXT_AREA_FIELD` + part |
| `TAB_CONTENT`, `TAB_SCROLL` | `TAB_BAR` + part `CONTENT` / `SCROLL` |
| `TAB_SELECTED`, `TAB_VERTICAL`, `TAB_VERTICAL_SELECTED`, `TAB_BAR_VERTICAL` | gone: `TAB` / `TAB_BAR`; renderer reads `isSelected` and orientation |

Watch out: a theme that wants *different decorations* per tab-bar orientation cannot express it through entries any
more; the decoration reads `component.orientation`. Accepted; revisit if a theme needs it. First casualty: the stock
tab strip was indented by one cell along its axis (`margin(0, 1)` horizontal, `margin(1, 0)` vertical); the default
theme cannot say that, so the strip is now flush with the bar's edge in every theme (the strip gains the two cells).
`SLIDER` and `NUMBER_INPUT` have one default entry each with a renderer that dispatches on `orientation`.

## Sessions

Four sessions, in this order. Each updates this doc's "Status" line, the api dump, and leaves a `trail note`.
The refactors in [component-refactoring-handover.md](component-refactoring-handover.md) (BinarySelector, HBox/VBox,
TextArea/ScrollBar) come after D.

### C. Role / variant / part split

- `ComponentRole` reduced to kinds; new `Variant`, `Part` enums; `ThemeKey(role, variant, part)` internal.
- `ComponentMetadata` / `CommonComponentProperties` / builders / `ComponentProperties` get `variant`, `part`.
- `ComponentTheme.entryFor(key)` exact + `resolveEntry(role, variant, part, kind)` with the fallback chain (default
  method, so every theme shares it).
- Fragments set `variant` / `part` on their parts; `AlertKind`, `BadgeStatus` map to variants.
- Tabs: drop the selected / vertical roles; `NeoTabRenderer` and `NeoTabBarRenderer` read `isSelected` / orientation.
- Neobrutalism entry map rewritten on triples; `ComponentFactory.configure` passes variant / part.
- Tests: `ComponentThemeTest` fallback cases, `NeobrutalismRenderTest` unchanged output.

### A. Default theme

- `ComponentThemes.default()`: an entry per (role, part) built from today's builder defaults
  (`defaultComponentRenderer`, `defaultDecorationRenderers`); builders read them from there.
- `ZirconDefaults.componentTheme` non-null, `Screen.componentTheme` non-null, `ComponentThemeOverride` non-null.
- Remove `hasThemeEntry`, the `entry == null` restore branch and `originalRenderingStrategy` in `DefaultComponent`
  (the default theme *is* the original strategy); `ColorTheme` still colors via `convertColorTheme` until B.
- Every theme falls back to the default theme through the fallback helper.

### B. Palette

- `PaletteKey`, `ComponentPalette` (interface + `componentPalette { }` builder, `isDark`, `overrides`, `withPrimary`).
- `ThemeEntry.styles: (ComponentPalette) -> ComponentStyleSet`; shared derivations in
  `api/component/theme/PaletteStyles.kt`; `ThemeEntryBuilder.styles { palette -> ... }`.
- Default theme entries get derivations replacing `convertColorTheme` (delete it from every component).
- `ColorTheme.toComponentPalette()`; `@Deprecated` on `ColorTheme` / `ColorThemes` / `themeProperty`.
- `Screen.componentPalette`, `ZirconDefaults.componentPalette`, `ComponentPaletteOverride`, propagation in
  `DefaultContainer`, `DefaultComponent` recompute on palette change (unless pinned).
- `ComponentTheme.mode` removed; `ThemeMode` resolution moves to palette factories.
- Neobrutalism: `NeobrutalismPalette` becomes two `ComponentPalette`s (`Palettes.neobrutalism(mode)`); `NeobrutalismTheme`
  styles via derivations; `NeobrutalismBuilder` loses `mode` / `palette`.
- Examples: the Neobrutalism page's Light / Dark / System buttons switch the palette.

### D. Bundled palettes and examples

- `Palettes`: Catppuccin latte / frappe / macchiato / mocha, Tokyo Night night / storm / moon / day, Kanagawa wave /
  dragon / lotus, from the hex tables in the research doc (primary sources cited there).
- Examples header: app-wide "Palette" selector next to the tileset selector (safe: shape unchanged); the component
  theme selector stays on the themes page because the header components are sized for the stock decorations.
- `ROADMAP.md`: ARIA state flags / role names, base16 import, terminal-following (ANSI-indexed) palette.

## Status

- 2026-11-16: planned.
- 2026-11-16: **C done.** `ComponentRole` holds kinds, `Variant` / `Part` / `ThemeKey` / `Orientation` added,
  `ComponentTheme.entryFor(key)` is exact and `resolveEntry(role, variant, part, kind)` walks the fallbacks, fragments
  and tab bars set variant / part / orientation, Neobrutalism keyed by `ThemeKey` with one `NeoTabRenderer`.
  `InputFieldBuilder.buttonRole` -> `buttonVariant`, `TextAreaFieldBuilder.action(text, variant)`. Next: A.
- 2026-11-16: **A done.** `ComponentThemes.default()` holds the stock renderers and decorations (one entry per kind
  plus `TAB`, `TAB_BAR`, `TAB_BAR#SCROLL`); builders start from it (`initialRenderer` is only for `NONE` kinds).
  `ComponentTheme.resolveEntry` walks the fallbacks in the theme, then in the default theme, merging entries with
  `ThemeEntry.over`. `componentTheme` is non-null on `ZirconDefaults`, `Screen`, `ComponentThemeOverride`, builders,
  `ComponentFactory`, `ThemedFragmentBuilder`; `DefaultComponent` lost `hasThemeEntry` / `originalRenderingStrategy`;
  the fragments' `lacks(...)` branches are gone (the tab look is `DefaultTabRenderer`). An explicit theme is pinned
  against the parent. Colors still come from `ColorTheme` when the resolved entry has no style set. Next: B.
- 2026-11-16: **B done.** `PaletteKey` (27), `ComponentPalette` (+ `componentPalette { }` builder deriving missing
  keys from `background` / `onBackground` / `primary`, `overrides`, `withPrimary`, `applyOverrides`), `PaletteStyles`,
  `ThemeEntry.styles: (ComponentPalette) -> ComponentStyleSet` (+ `stylesFor`), `ColorTheme.toComponentPalette()`,
  `Palettes.neobrutalism(mode)`, `ComponentPaletteOverride` on components, containers and the screen, propagation
  and pinning like the theme, `ZirconDefaults.componentPalette`, `ComponentRenderContext.palette`.
  `ComponentTheme.mode`, `NeobrutalismPalette`, `NeobrutalismBuilder`, `convertColorTheme` removed; `ColorTheme`,
  `ColorThemes`, `ColorThemeOverride`, `ZirconDefaults.theme`, builders' `colorTheme` deprecated. The Neobrutalism
  example's Light / Dark / System buttons switch the palette. Watch: input fields are now sunken wells with the
  selection colors while focused (1.x inverted them); check the examples app by eye. Next: D.
- 2026-11-16: **D done.** `Palettes.catppuccin(flavor, accent)` (4 flavors, 14 accents), `tokyoNight(style)` (4),
  `kanagawa(variant)` (3), each with a `(mode: ThemeMode)` overload picking the light / dark member, and
  `Palettes.all()`; hex values from the primary sources (`palette.json` v1.8.0, tokyonight `colors/*.lua` with `day`
  from the generated helix extra, kanagawa `colors.lua` / `themes.lua`), mapping per project style guide in
  `BundledPalettes`. The examples header's "Theme" selector became "Palette": the 13 bundled palettes, then the 77
  colour themes adapted; `ExamplesScreen.themeProperty` -> `paletteProperty`. Dialog modals pin
  `screen.componentPalette`. Not done: builders exposing each project's native color names (`accent = MAUVE` is
  there as the Catppuccin accent enum; a full named-color DSL waits for a need, see ROADMAP). Palette plan complete;
  next are the refactors in `component-refactoring-handover.md`.

**Status (2026-11-17): `ColorTheme` is gone.** `ColorTheme`, `ColorThemes`, `ColorThemeResource`, the adapter, the
builders' `colorTheme`, `ZirconDefaults.theme`, `screen.theme`, `Group.theme`, `View.theme` and
`ComponentRenderContext.theme` are removed; colors come only from `ComponentPalette` (default
`Palettes.catppuccin()`). The 77 legacy five-color themes are not bundled any more; new palettes and the
color fine-tuning are a session of their own.

**Status (2026-11-17): `SystemTheme`.** `ThemeMode.SYSTEM` resolves through the live `SystemTheme` object
(`modeProperty`, `paletteProperty`, `update`, `refresh`, `reset`); the terminal renderer, `TileGridView` and the
browsers write it, `ZirconDefaults.systemThemeDetector` is the fallback `refresh()` samples. `Screen.followSystemPalette
{ mode -> ... }` (and `ZirconDefaults.followSystemPalette`) keeps a screen on the host palette or the fallback pair until
the palette is set explicitly. The examples' selector has a `System` entry; `SystemPaletteFollower` and
`useTerminalPalette` are gone. See `docs/system-theme-handover.md`.
