---
title: "zircon.terminal built 2026-10-29: layout, pty harness for end-to-end checks, native 3x slower than HotSpot per cell"
author: Bruce
date: 2026-10-29
branch: master
cost: { minutes: 23, tokens: 238680 }
id: l-677b717a
scope: repo
status: active
src: agent
touches:
  - TODO.md
---

## Decided
- `zircon.terminal` layout: `CellBuffer` (flat IntArrays) ← `TextTileset`/`TextTilesetLoader` ← `TerminalRenderer : BaseRenderer` → `AnsiEncoder` (diff + SGR runs + `?2026` sync) → `Tty`. `TerminalSession` owns the loop and is public so tests drive it with a scripted `Tty`; `runTerminal` returns it with frame/render/encode/write stats.
- Kitty flags pushed are 31 (disambiguate + event types + alternate keys + all keys as escapes + text). Handshake = `CSI ?u` + `CSI c`; DA1 reply terminates the wait, missing flags reply = fail fast.
- Examples: `zircon.examples` is core-only (jvm, wasmJs, linux, macos) with data files in `zircon.examples/resources/files`; `zircon.examples.compose` holds `ExamplesApp` + Compose resources via `compose.resources.customDirectory` pointing at that folder; `zircon.examples.terminal` reads the same files from disk through `ZIRCON_EXAMPLES_FILES` (run tasks set it, `make demo-terminal` runs the binary). `Demo.textOnly = false` marks demos the terminal launcher hides (Game area).
- Goldens for the terminal are `.ansi` text with ESC shown as `␛` in `zircon.terminal/src/jvmTest/resources/golden`, same `UPDATE_GOLDENS=1` flow as zircon.compose.

## Tried and ruled out
- Benchmarking through `:zircon.examples.terminal:jvmRun`: Gradle gives the process no tty. The scratch Python pty harness (openpty + TIOCSWINSZ + answer `CSI ?u` with `CSI ?31u` + DA1, setsid + TIOCSCTTY so `/dev/tty` exists for the JVM `stty` path, reader thread so the pty never blocks writes) is what works; a slow reader halves the fps.
- Measuring the "tick < 4 ms" bar on native with the Benchmark demo: `render` includes the demo's own 32k `layer.draw` calls, and Kotlin/Native runs the same per-cell code ~3x slower than HotSpot (encode 3.1 ms vs 1.2 ms). Judge the bar on the JVM; native numbers are in the TODO.md results log.

## Watch out
- `zircon.examples.compose` keeps `src/commonMain/composeResources` empty on purpose; the files come from `customDirectory`. Moving files back under `composeResources/files` would nest a second `files/` level (happened once during the split).
- `Demo`'s `textOnly` parameter must stay before `show` so the trailing-lambda call sites compile.
- The by-hand smoke test on foot/ghostty/alacritty/kitty/wezterm (`zircon.terminal/README.md`) has not been run yet; the pty harness only proves the protocol and the restore path.
