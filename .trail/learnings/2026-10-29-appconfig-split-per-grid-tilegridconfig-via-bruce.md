---
title: "AppConfig split: per-grid TileGridConfig via tileGrid {}, process-wide ZirconDefaults via zirconDefaults {}; window settings are runDesktop params"
author: Bruce
date: 2026-10-29
branch: master
cost: { minutes: 24, tokens: 137834 }
id: l-6993a762
scope: repo
status: active
src: agent
touches:
  - ROADMAP.md
---

## Decided
- `AppConfig` was two things at once: per-grid settings (size, tileset, cursor blink) and process-wide defaults that the hidden `RuntimeConfig` singleton exposed to every builder (default tileset/theme, debugMode, debugConfig, shortcutsConfig). Split on 2026-10-29 into `api/grid/TileGridConfig.kt` (immutable, built by `TileGridBuilder`, the grid's `config`) and `api/config/ZirconDefaults.kt` (immutable snapshot, `ZirconDefaults.current`, installed by `zirconDefaults { }` in `api/builder/config/ZirconDefaultsBuilder.kt`).
- Building a grid no longer mutates globals. `tileGrid { }` is the only way to a grid; `createTileGrid`, `appConfig { }`, `AppConfigBuilder`, `withAppConfig` are gone. `tileGridConfig { }` builds a config without a grid (tests, `TestRenderer`).
- Window settings (`title`, `fullScreen`) are not Zircon's concern: they are parameters of `runDesktop` in `zircon.compose/src/jvmMain/.../RunDesktop.kt`. `iconPath` dropped; use your own `Window` with `TileGridView` for that.
- Deleted as unread by anything: `isClipboardAvailable`, `tilesetFactories` (compose takes `extraTilesetFactories` on `TileGridView` instead), `defaultGraphicalTileset`, `customProperties`/`AppConfigKey`/`CustomPropertyBuilder`. Clipboard went to ROADMAP.md.
- `DebugConfig`/`ShortcutsConfig` live in `api/config`, their builders in `api/builder/config`; `TilesetFactoryBuilder` in `api/builder/tileset`; `RenderData` moved to `internal/renderer` because only the internal `Renderer` uses it.

## Tried and ruled out
- Keeping the global hidden behind `TileGridBuilder.build()`: smallest diff, but the second grid built would silently reset defaults for the first. Rejected for a model with several `TileGridView`s per window.
- Killing the global entirely: components already inherit tileset/theme from their parent on attach (`DefaultContainer`), but `DefaultAnimationRunner`, `UIEventToComponentDispatcher`, `BaseView` and the graphics builders have no grid handle. Left for later; `ZirconDefaults.current` is the single place to swap when that happens.
- Mutable `object ZirconDefaults { var ... }`: the user preferred an immutable snapshot so internals read a stable value.

## Watch out
- `ZirconDefaults.current` is read at construction time (`private val debug = ZirconDefaults.current.debugMode` in `DefaultAnimationRunner`, `shortcutsConfig` in `UIEventToComponentDispatcher`). Call `zirconDefaults { }` before building the first grid or it is ignored there.
- `TileGridView` reads `ZirconDefaults.current.debugConfig.displayGrid` every frame and does not gate on `debugMode`, same as before the split.
- Inside `tileGrid { }` a caller-side `tileset` or `size` variable shadows nothing: the builder property wins, so write `this.tileset = tileset` (see `ComponentIntegrationTestBase`, `Demos.kt`).
