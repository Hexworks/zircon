---
title: "AppConfig split: per-grid TileGridConfig via tileGrid {}, process-wide ZirconDefaults via zirconDefaults {}; window settings are runDesktop params"
author: Bruce
date: 2026-10-29
branch: master
cost: { minutes: 12, tokens: 9178 }
id: l-f95294e1
scope: repo
status: active
replaces: l-6993a762
src: agent
touches:
  - README.md
  - ROADMAP.md
  - TODO.md
  - zircon.compose/api/zircon.compose.api
  - zircon.compose/src/commonMain/kotlin/org/hexworks/zircon/compose/TileGridView.kt
  - zircon.compose/src/jvmMain/kotlin/org/hexworks/zircon/compose/RunDesktop.kt
  - zircon.compose/src/jvmTest/kotlin/org/hexworks/zircon/compose/GoldenRenderTest.kt
  - zircon.compose/src/jvmTest/kotlin/org/hexworks/zircon/compose/TileGridViewTest.kt
  - zircon.core/api/zircon.core.api
---

## Decided
- `AppConfig` mixed per-grid settings with process-wide defaults exposed through the hidden `RuntimeConfig` singleton. Split on 2026-10-29 into `api/grid/TileGridConfig.kt` (size, tileset, cursor blink; built by `TileGridBuilder`, the grid's `config`) and `api/config/ZirconDefaults.kt` (tileset, theme, debugMode, debugConfig, shortcutsConfig; immutable snapshot at `ZirconDefaults.current`, installed by `zirconDefaults { }`).
- Building a grid never mutates globals. `tileGrid { }` is the only way to a grid; `createTileGrid`, `appConfig { }`, `AppConfigBuilder`, `withAppConfig` are gone. `tileGridConfig { }` builds a config without a grid.
- Window settings (`title`, `fullScreen`) are `runDesktop` parameters in `zircon.compose`; `iconPath` dropped.
- Deleted as unread: `isClipboardAvailable`, `tilesetFactories` (compose takes `extraTilesetFactories` on `TileGridView`), `defaultGraphicalTileset`, the custom-properties cluster. Clipboard is on ROADMAP.md.
- Internals read `ZirconDefaults.current` live through getters (`DefaultAnimationRunner.debug`, `UIEventToComponentDispatcher.shortcutsConfig`, `DefaultContainer` bounds check), so `zirconDefaults { }` can be called at any time.

## Tried and ruled out
- Keeping the global hidden behind `TileGridBuilder.build()`: the second grid built would silently reset defaults for the first.
- Killing the global entirely: `DefaultAnimationRunner`, `UIEventToComponentDispatcher`, `BaseView` and the graphics builders have no grid handle. `ZirconDefaults.current` is the one place to swap when that happens.
- Mutable `object ZirconDefaults { var ... }`: user preferred an immutable snapshot.

## Watch out
- `BaseView.theme` copies `ZirconDefaults.current.theme` at construction on purpose: it is a `var` the view owns afterwards.
- `TileGridView` reads `ZirconDefaults.current.debugConfig.displayGrid` every frame and does not gate on `debugMode`, same as before.
- Inside `tileGrid { }` a caller-side `tileset`/`size` variable is shadowed by the builder property: write `this.tileset = tileset`.
