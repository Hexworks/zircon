---
title: "A default component theme is always active: builders start from it, lookups cascade aspect by aspect into it, an explicit theme is pinned, and the stock tab st…"
author: Bruce
date: 2026-11-16
branch: master
cost: { minutes: 29, tokens: 328611 }
id: l-9efc7d89
scope: repo
status: active
src: agent
touches:
  - docs/component-palettes.md
  - docs/component-themes.md
---

## Decided
Session A of the palette plan (2026-11-16). `ComponentThemes.default()` is a singleton holding the stock renderer and decorations per component kind (plus TAB, TAB_BAR, TAB_BAR#SCROLL) and no colors. Builders take their defaults from it at construction (`initialRenderer` is only for kinds no theme knows, i.e. role NONE custom components). `componentTheme` is non-null on `ZirconDefaults`, `Screen`, `ComponentThemeOverride`, builders, `ComponentFactory`, `ThemedFragmentBuilder`.

Resolution merges: `resolveEntry` collects the entries found along `ThemeKey.fallbacks(kind)` in the theme, then the same chain in the default theme, and folds them with `ThemeEntry.over` (a null aspect is taken from the next entry). A theme therefore writes only what it changes, and switching from theme X to Y can never leave X's renderer on a component (the old "null = keep what the component has" leaked on switches). Only role NONE resolves to null.

Pinning: `componentTheme = x` on a builder or component is kept whatever the parent's theme is or becomes (`inheritComponentTheme` is a no-op for it). The old rule ("a parent with a theme wins, a null parent does not clear") has no null to distinguish any more. The Neobrutalism example relies on this: its root is pinned so the examples app around it keeps the stock look.

Colors until session B: a resolved entry without a style set leaves colors to the `ColorTheme` (`convertColorTheme`), which is how the default theme colors things; `themeOwnsColors` replaces `hasThemeEntry` in `DefaultComponent`.

## Tried and ruled out
- Applying the theme entry at `presetTheme` time (so blocks see the theme's decorations when sizing): a block that then sets `role = NONE` (tab roots) kept the kind's decorations, since NONE resolves to nothing and nothing resets them. Entries are applied at `createMetadata` (and by `configure`), as before; fragments size with `configure` + `decorationsSize`.
- Keeping the tab strip's one-cell indent (`margin(0, 1)` horizontal, `margin(1, 0)` vertical) as a fragment default with a `decorations = null` TAB_BAR entry: the chain fills null from the HBOX/VBOX kind entry (empty), so the margin is removed anyway. Giving HBOX/VBOX entries null decorations would have leaned on an incomplete default theme. Decorations cannot vary per orientation (fixed `occupiedSize`), so the indent is gone in every theme; the strip gains the two cells. `TabStripTest` bars shrank by 2 to keep the same capacities.
- Orientation-specific builders keeping their own renderer (SLIDER, NUMBER_INPUT): would have left the default theme incomplete; `OrientedComponentRenderer` dispatches on `component.orientation` instead.

## Watch out
- A theme entry can no longer say "keep the component's own decorations"; every aspect resolves to something. Fragment defaults set with `defaultDecorationRenderers` survive only when no entry along the chain has decorations, which with a complete default theme means never (except role NONE).
- `BaseComponentBuilder` reads the default theme in its constructor; the default theme must not touch `ZirconDefaults` while initializing (cycle), its renderers are shared singletons and must stay stateless.
- `ThemeEntry.decorationRenderers` are in strategy order (outermost first) and `applyThemeEntry` sets them raw, while the builder's `decorationRenderers` setter reverses its input. Pre-existing; `ThemeEntryBuilder.decorations {}` claims the builder order but does not reverse.
- Pre-existing, unrelated failures on this machine: `ExamplesScreenTest.aRebuiltScreenKeepsTheSelection` and `aTilesetChangeReachesTheGridAndTheComponents` (same at HEAD 537b2367b).

In the code:
- `zircon.core/.../internal/component/theme/DefaultComponentTheme.kt`, `internal/component/renderer/DefaultTabRenderer.kt`, `OrientedComponentRenderer.kt`
- `api/component/theme/ComponentTheme.kt` (`resolveEntry`), `ThemeEntry.kt` (`over`), `api/ComponentThemes.kt` (`default()`), `api/builder/config/ZirconDefaultsBuilder.kt` (`DEFAULT_COMPONENT_THEME`)
- `api/component/builder/base/BaseComponentBuilder.kt` (init from the default theme, `presetTheme`), `internal/component/impl/DefaultComponent.kt` (`themeOwnsColors`, `inheritComponentTheme`)
- tests: `ComponentThemeTest` (`the_default_theme_is_the_stock_look_and_is_always_on`, `a_missing_aspect_falls_through_the_chain_to_the_default_theme`, `a_switch_between_themes_does_not_leak_the_old_renderer`, `an_explicitly_set_theme_is_pinned_against_the_parent`), `DefaultThemeTabRenderTest`
- docs: `docs/component-palettes.md` (decisions, Watch out, Status), `docs/component-themes.md` (*(changes)* markers)
