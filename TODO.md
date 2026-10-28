# Zircon 2.0 plan

Living handover document. Update the **Status** section whenever a step lands so a fresh
session (or person) can continue without re-deriving decisions. Decisions were made in the
2026-10-28 planning session; the *why* for each lives in `.trail/learnings/`.

## Status

- Current milestone: **M3 core cleanup** (M1 and M2 done 2026-10-28)
- M2 state: cobalt 2.0.0 is committed locally in `~/projects/cobalt` and published to `~/.m2`
  (all 12 targets). **Still to do by hand:** push cobalt and cut a GitHub release so the
  `Publish` workflow uploads it to Maven Central (needs the signing/Central secrets).
- Next step: collapse `Application`/`VirtualApplication`/`ContinuousRenderingApplication` into
  TileGrid + AppConfig + a renderer/tick seam (see M3 list). Core tests: 966 green on jvm, 956 on js/wasmJs/linuxX64.
- `prototype/` stays in the repo as the reference for M4 and is deleted when M4 lands.
- Branch: `master` (no feature branches)

## Decisions

| Area | Decision |
|---|---|
| Renderer | Compose Multiplatform only. `zircon.korge` and `zircon.examples.korge` are deleted. |
| Targets | `zircon.core`: every KMP target. `zircon.compose`: jvm, android, wasmJs (no js, Compose web is wasm-only). |
| Entry point | Composable-first: a `@Composable` the user drops into their own tree. JVM-only `runDesktop {}` convenience. |
| Core app model | `Application`, `VirtualApplication`, `start()` and the continuous loop go away. Core keeps `TileGrid`, `Screen`, `AppConfig` and a backend-neutral renderer/tick seam. |
| Rendering | The critical path. Prototype both a common `DrawScope` path and a CPU pixel-buffer path; the benchmark picks. Bar: 1920x1080 at 8x8 tiles (32,400 tiles), full redraw every frame, 60 fps on desktop and wasm, 30 fps on a mid-range Android phone. |
| Assets | All formats stay: CP437 PNG, TrueType, graphical zip tilesets, REXPaint `.xp`, `.zap` animations. Metadata moves from YAML to JSON (kotlinx-serialization). Inflate/zip/gzip implemented in-house (port of puff/tinf). Bundled files ship in `zircon.compose` as Compose resources; core exposes a `loadBytes` seam and `ByteArray` APIs. |
| Cobalt | Cobalt 2.0 is released first (`~/projects/cobalt`): all targets, drop reflect and logging libraries, Java 17 bytecode, Maven Central. |
| Dependencies | Drop korge, kotlin-reflect (jvmTest only), kotlin-logging, slf4j, logback, mockk. Keep cobalt, kotlinx-collections-immutable, kotlinx-coroutines (declared explicitly), kotest. Add kotlinx-serialization-json. |
| Versions | Kotlin 2.4.21, Gradle 9.8.2, JDK 25 toolchain with Java 17 bytecode, Compose Multiplatform 1.12.2, AGP 9.5.0, vanniktech publish 0.37.0. |
| API | Bare-noun DSL everywhere (`button {}`, `hBox {}`); `buildX` functions are deleted. `explicitApi()` plus binary-compatibility-validator. No `internal` type in a public signature. Kotlin-only, no Java interop annotations beyond what the JVM needs. |
| Duplicates | `Position(x, y)` and `Size(w, h)` factory functions replace `create` and `position {}`. One loader per tileset type. `Screen` only via `tileGrid.toScreen()`. |
| Features | Implement `Blink` and `Hidden`. Add the `DebugConfig` grid overlay. Delete Border, Underline and CrossedOut. `Tile` becomes a sealed class. Transparency is removed. Shortcut activation for interactive components. Theming rework is deferred to 2.x. Nerd glyphs stay in core; code points are stored as `Int`. |
| Examples | Shared `zircon.examples` library plus thin `zircon.examples.desktop`, `.android`, `.web` launchers. |
| Tests | Golden-image tests on desktop JVM. Android and wasm are checked by running the examples before release. |
| Release | Single `2.0.0` at the end. CI on JDK 25. |

## Milestones

### M1: renderer prototype (`prototype/`, standalone build, deleted afterwards)

- [x] Standalone Gradle build with jvm and wasmJs targets, latest Kotlin and Compose
- [x] Shared benchmark harness: 240x135 grid, every tile changes every frame, on-screen fps (`PROTO_AUTO=1` / `?auto` prints a RESULT line per path)
- [x] Path A: common `DrawScope` (`drawImage` per tile from a sprite sheet, `drawRect` backgrounds)
- [x] Path B: CPU pixel buffer, `expect`/`actual` upload to `ImageBitmap`
- [x] Path C/C2: whole grid as triangle meshes via Compose `Vertices` and via native `drawVertices` with flat arrays
- [x] Path D: three 240x135 data textures (code, fg, bg) + `RuntimeEffect` shader sampling the atlas, one `drawRect`
- [x] Measure on desktop and wasm; results below
- [x] Decision: **Path D**. Android < API 33 (no `RuntimeShader`) falls back to C2 (`android.graphics.Canvas.drawVertices`) or B (`Bitmap.setPixels`, no interop penalty on ART).

### M2: cobalt 2.0

- [x] Add wasmJs, iOS, macOS, linuxArm64, mingwX64 targets (no androidTarget: Android consumers use the jvm variant)
- [x] Remove kotlin-reflect, kotlin-logging, slf4j, logback; `Logger`/`LoggerFactory` kept, new `LogLevel` + pluggable `LogSink`
- [x] Java 17 bytecode, Kotlin 2.4.21, Gradle 9.8.2, JDK 25; 111 tests green on jvm, js, wasmJs, linuxX64
- [ ] Publish to Maven Central (push + GitHub release triggers `Publish`; manual step)

### M3: core cleanup

- [x] Upgrade Kotlin, Gradle, JDK toolchain, plugins; remove the local JDK path from `gradle.properties`
- [x] Delete `zircon.korge`, `zircon.examples.korge`, the stray `hs_err_pid*.log` (and the old `zircon.compose` + example; M4 rewrites them, old input mapping is in git history at `9fd8c8dc0`)
- [x] Drop korge-core: time via `kotlin.time`, math helpers via stdlib, `Resource.load()` becomes `Resources.loader` + `Resource.loadBytes()`
- [x] In-house inflate, zip reader, gzip reader (`internal/util/compression`), unit tests + bundled `.xp` test
- [ ] JSON metadata for graphical tilesets and animations; convert `tileinfo.yml` and `ANIMATIONS.md` sample
- [x] Drop kotlin-reflect (jvmTest only), logging libraries, mockk; add kotlinx-serialization-json; bump kotlinx-collections-immutable
- [ ] Remove `Application`, `VirtualApplication`, continuous loop; introduce the renderer/tick seam
- [ ] DSL naming: bare nouns, delete `buildX`, fix `hBox`/`vBox`, remove `withX` leftovers
- [ ] Deduplicate creation paths (Position, Size, Screen, tilesets)
- [ ] `explicitApi()` and binary-compatibility-validator; move `Base*` implementations out of `api`; no internal types in public signatures
- [ ] `Tile` sealed class; remove transparency
- [ ] Implement `Blink` and `Hidden`; delete Border, Underline, CrossedOut
- [ ] Shortcut activation for interactive components
- [ ] Nerd glyphs: `Int` code points
- [ ] Known bugs: `ModalBuilder.close()` infinite recursion, `DropdownMenuBuilder.position` throws, RadioBox first click, fast CheckBox click
- [ ] All tests green on jvm, js, wasmJs

### M4: zircon.compose on the chosen path (desktop)

- [ ] Rewrite the renderer on the M1 winner, all in `commonMain`
- [ ] Compose resources for bundled assets (`publicResClass = true`)
- [ ] All four tileset types, modifiers, blink, debug grid
- [ ] Input: keyboard, pointer, including `MOUSE_CLICKED`
- [ ] Golden-image tests
- [ ] `runDesktop {}` convenience

### M5: wasm, android, examples

- [ ] android target (AGP 9 KMP library plugin), wasmJs target
- [ ] `zircon.examples` library with every demo (components, REXPaint, animation, benchmark)
- [ ] Desktop, Android and web launchers; run each by hand

### M6: docs, CI, release

- [ ] README rewrite for the Kotlin DSL and Compose entry point
- [ ] GitHub Actions: JDK 25, `check` on PR, publish on tag
- [ ] Freeze the BCV dump, publish `2.0.0`

## Results log

Append benchmark numbers and notable findings here, newest first.

### 2026-10-28 M1 renderer benchmark

Machine: Ryzen 9 7940HS, Radeon 780M (RADV). 240x135 grid, 8x8 tiles, 32,400 tiles, every tile
rewritten every frame, random colours (16-colour palette gave the same numbers). Desktop with
`-Dskiko.vsync.enabled=false`; wasm in headless Chrome via ANGLE/Vulkan (`scratchpad/bench.js`).

| Path | Desktop JVM | wasmJs |
|---|---|---|
| A1 `DrawScope.drawRect`+`drawImage` per tile | 7 fps, 16 ms record | 5 fps, 46 ms |
| A2 same via `Canvas` + reused `Paint` | 6.5 fps | 5 fps |
| B CPU BGRA buffer + `Bitmap.installPixels` | 150 fps, 3.3 ms fill + 0.5 ms upload | 5 fps, 27 ms fill + **150 ms upload** |
| C Compose `Vertices` (List-based) | 80 fps, 10 ms | 18 fps, 53 ms |
| C2 skia `drawVertices`, `FloatArray`/`IntArray` | 200 fps, 2.8 ms + 2.7 ms native | 29 fps, 32 ms (28 ms native) |
| **D data-texture `RuntimeEffect` shader** | **~1,800 fps, < 0.5 ms** | **95–108 fps, 9 ms (7 ms upload)** |

Findings:
- Per-tile draw calls are hopeless at this scale on every backend (65k calls/frame).
- On wasm the dominant cost is bytes crossing from Kotlin/Wasm into Skiko per frame: 8 MB (B) costs
  ~150 ms, 5.5 MB (C2) ~28 ms, 390 KB (D) ~7 ms. Design rule: per-frame data must scale with
  cells, not pixels.
- D's shader: `floor(p / tile)` picks the cell, samples code/fg/bg from the data textures with
  nearest filtering, then samples the atlas at `glyphOrigin + inTile`. Grid geometry is baked into
  the SkSL source so no uniforms are packed per frame.
- Spare channels in the code texture (G, B, A) are free for per-tile flags (blink, hidden, flip,
  atlas index for graphical tilesets). TrueType becomes a CPU-rasterised glyph atlas + slot lookup.
- Multiple layers: one `drawRect` per layer with the shader writing alpha 0 for empty cells.
