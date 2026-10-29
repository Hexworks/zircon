---
title: "Examples app is Zircon-only: one full-window grid per demo, tab bar inside the grid, fps as a layer"
author: Bruce
date: 2026-10-29
branch: master
cost: { minutes: 13, tokens: 193946 }
id: l-ba5506ac
scope: repo
status: active
src: agent
touches:
  - zircon.examples/src/commonMain/kotlin/org/hexworks/zircon/examples/Demos.kt
---

## Decided
- `zircon.examples` has no Compose controls (material3 dependency dropped). `ExamplesApp` in `zircon.examples/src/commonMain/kotlin/org/hexworks/zircon/examples/Demos.kt` is a `BoxWithConstraints` that builds one `TileGrid` sized to the available pixels divided by the demo's tileset, with a Zircon `horizontalTabBar` on top (3 rows) and the demo below. Full HD with 8x8 tiles gives 240x135 (not 247x135: 1920/8 = 240).
- Every demo gets its own grid and the grid is rebuilt on tab change or resize (debounced 150 ms). Reason: `Screen.tileset` must match the grid tileset size (`TileGridScreenTest.shouldThrowExceptionWhenTilesetSizeIsIncompatible`), and the benchmark is 8x8 while the rest are 16x16.
- `TabBar` (core) gained `selectedKeyProperty` / `selectedKey` so the app can react to tab clicks; both default tab bars keep it in sync with the radio group. API dump updated.
- Demos that draw (game area, REXPaint, animation, benchmark) use layers added to the screen, offset to the content area below the tab bar; layers render above components, so the tab bar's content panel does not hide them. Only the components demo is a real tab content.
- The fps counter is a 8x1 `layer`, not a `label`: components may not overlap and the tab bar's root spans the whole screen.
- Grid tileset is always a CP437 one (wanderlust/rexPaint 16x16, rexPaint 8x8); the nethack graphical tileset goes on the game-area layer, because the tab bar text would render through a graphical tileset otherwise.

## Tried and ruled out
- Drawing demo content with `screen.draw` on the base surface: the tab bar's content `panel` has a default (opaque) renderer and covers it.

## Watch out
- `zircon.examples/src/jvmTest/.../ExamplesScreenTest.kt` renders every demo at 1920x1080 into `zircon.examples/build/demo-renders/*.png`; look there to eyeball a layout change without opening a window.
