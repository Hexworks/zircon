---
title: Renderer for 2.0 is a data-texture RuntimeEffect shader; per-tile draw calls and CPU pixel buffers fail on wasm
author: Bruce
date: 2026-10-28
branch: master
cost: { minutes: 81, tokens: 238338 }
id: l-75e58f75
scope: repo
status: active
src: agent
touches:
  - TODO.md
  - prototype/settings.gradle.kts
  - prototype/gradle/libs.versions.toml
  - prototype/gradle.properties
  - prototype/build.gradle.kts
  - prototype/src/commonMain/kotlin/Grid.kt
  - prototype/src/commonMain/kotlin/Atlas.kt
  - prototype/src/commonMain/kotlin/FrameStats.kt
  - prototype/src/commonMain/kotlin/DrawScopePath.kt
  - prototype/src/commonMain/kotlin/PixelPath.kt
---

## Decided
Zircon 2.0's Compose renderer draws the whole tile grid with one `drawRect` and a `RuntimeEffect` (SkSL) shader that samples three small RGBA data textures (char code, fg, bg; one texel per cell) and the glyph atlas. Reference implementation: `prototype/src/skikoMain/kotlin/DataTextureRenderer.skiko.kt` with the common half in `prototype/src/commonMain/kotlin/ShaderPath.kt`. Measured 2026-10-28 on a 240x135 grid at 8x8 (32,400 tiles, full rewrite every frame): ~1,800 fps desktop, ~100 fps wasmJs (Chrome, ANGLE/Vulkan). Android below API 33 has no RuntimeShader and falls back to native `drawVertices` with flat arrays (prototype path C2) or `Bitmap.setPixels` (path B).

## Tried and ruled out
- Per-tile `DrawScope.drawImage`/`drawRect` (paths A1/A2 in `prototype/src/commonMain/kotlin/DrawScopePath.kt`): 7 fps desktop, 5 fps wasm. 65k draw calls per frame is the problem, not the API flavour.
- CPU BGRA pixel buffer + `Bitmap.installPixels` (path B, `PixelPath.kt`): 150 fps on desktop but 5 fps on wasm because copying the 8 MB frame from Kotlin/Wasm into Skiko costs ~150 ms.
- Compose `Vertices` mesh (path C, `VerticesPath.kt`): 80 fps desktop, 18 fps wasm; the List<Offset>/List<Color> API boxes 260k values per frame.
- Native skia `drawVertices` with FloatArray/IntArray (path C2, `NativeVerticesPath.kt`): 200 fps desktop, 29 fps wasm; still moves 5.5 MB of vertex data per frame.

## Watch out
- On wasmJs the cost that matters is bytes crossing from Kotlin/Wasm into Skiko each frame (~17 ms per MB for ByteArray). Any renderer design must send data proportional to cells, never pixels.
- Benchmark with `-Dskiko.vsync.enabled=false` on desktop, otherwise everything reads as 60 fps. The prototype's `PROTO_AUTO=1` env (desktop) and `?auto` query (wasm) print one RESULT line per path.
- Headless Chrome needs `--use-angle=vulkan --enable-gpu --ignore-gpu-blocklist` to use the real GPU; check the RENDERER line the bench script prints is not SwiftShader.
