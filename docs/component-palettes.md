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
  `isSelected`, colored from `selection / onSelection`; a vertical tab bar is `role = TAB_BAR` and the renderer reads the
  orientation. ARIA-style state flags and ARIA role names are deferred (ROADMAP).
- **Lookup fallback: part, then variant, never role.** `(role, variant, part)` -> `(role, variant, NONE)` ->
  `(role, DEFAULT, part)` -> `(role, DEFAULT, NONE)`, first in the theme, then the same chain in the default theme.
  The *variant* still reaches the derivation even when the entry is a fallback, so a `BUTTON PRIMARY` without its own
  entry gets the default button shape in primary colors.
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
| `primaryBackground` | `BACKGROUND`; `SURFACE_SUNKEN` = shaded |
| `secondaryBackground` | `SURFACE`; `SURFACE_RAISED` = tinted |
| `primaryForeground` | `ON_BACKGROUND`, `ON_SURFACE`, `ON_SURFACE_RAISED`, `ON_SELECTION` |
| `secondaryForeground` | `TEXT_MUTED`, `BORDER`; `TEXT_DISABLED` = toned |
| `accent` | `PRIMARY`, `ACCENT`, `BORDER_FOCUSED`; `ON_PRIMARY` / `ON_ACCENT` = `primaryBackground`; `SECONDARY` = desaturated; `SELECTION` = blended 40% toward `primaryBackground` |
| (none) | `INFO` / `SUCCESS` / `WARNING` / `ERROR` = fixed blue / green / yellow / red, `ON_*` = `primaryBackground` |

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
more; the decoration reads the orientation. Accepted; revisit if a theme needs it.

## Sessions

Four sessions, in this order. Each updates this doc's "Status" line, the api dump, and leaves a `trail note`.
The refactors in [component-refactoring-handover.md](component-refactoring-handover.md) (BinarySelector, HBox/VBox,
TextArea/ScrollBar) come after D.

### C. Role / variant / part split

- `ComponentRole` reduced to kinds; new `Variant`, `Part` enums; `ThemeKey(role, variant, part)` internal.
- `ComponentMetadata` / `CommonComponentProperties` / builders / `ComponentProperties` get `variant`, `part`.
- `ComponentTheme.entryFor(role, variant, part)` with the fallback chain (shared helper used by every theme).
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

Planned 2026-11-16. Nothing implemented yet.
