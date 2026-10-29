---
title: "Render tick is per-tile work times 32k cells: no boxing, no string keys, no per-tile lists in BaseRenderer and the tile map"
author: Bruce
date: 2026-10-29
branch: master
cost: { minutes: 14, tokens: 92981 }
id: l-a5b28d2f
scope: repo
status: active
src: agent
touches:
  - zircon.examples/build.gradle.kts
---

## Decided
- `ArrayBackedTileMap.get` (`zircon.core/.../internal/graphics/ArrayBackedTileMap.kt`) returns -1 from a private `indexOf` instead of an `Int?`: the nullable boxed an Integer on every lookup, one per cell per layer per frame, 45% of render time on the JVM (JFR with `-XX:+DebugNonSafepoints`; plain JFR blamed `fetchLayers` through inlining).
- `TilesetRegistry.loadTilesetFrom` (zircon.compose) keeps 4 identity-checked recent (resource, tileset) slots in front of the `"path@WxH"` string key the map uses; the renderer calls it once per tile.
- `Char.CP437Index()` reads a flat `IntArray` built from the `mapOf` table once.
- `FrameData.writeCell` guards `usedAtlases` with a `BooleanArray(256)` so the set is touched once per atlas per frame, not per tile.
- `BaseRenderer.renderAllTiles` collects a cell's tiles top first into two reused `ArrayList`s and draws them in reverse, bounds-checks a layer with ints before allocating a `Position`, reads each layer's size and tileset once per frame (`RenderLayer`), and `renderTile` only touches `modifiers` / `isBlinking` when the modifier set is non-empty.
- Numbers, JVM, 240x135 Screen with tab bar: render 3.15 ms -> 0.96 ms per frame; bare grid 2.3 -> 0.9; static 120x67 components screen 1.09 -> 0.43. Benchmark demo fill 1.6 -> 0.24 ms after switching to a prebuilt tile palette (`BenchmarkDemo.kt`).

## Watch out
- What remains is spread thin: the array read itself, `isOpaque`, `writeCell`, `ArrayList.clear`, and the per-layer bounds loop (26% on a screen with 18 renderables). The next step, if ever needed, is culling renderables per row rather than per cell.
- Profile harness: a jvmTest that loops `ComposeRenderer.render(frame)` under `jdk.jfr.Recording`, with `tasks.withType<Test> { jvmArgs("-XX:+UnlockDiagnosticVMOptions", "-XX:+DebugNonSafepoints") }` added temporarily to `zircon.examples/build.gradle.kts`; aggregate `jfr print --events jdk.ExecutionSample` by top frame. Without `DebugNonSafepoints` the attribution is wrong.
