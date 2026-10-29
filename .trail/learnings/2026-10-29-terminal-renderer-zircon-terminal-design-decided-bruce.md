---
title: "Terminal renderer (zircon.terminal) design decided 2026-10-29: text-only cells, kitty keyboard required, diff+sync output, ships in 2.0.0"
author: Bruce
date: 2026-10-29
branch: master
cost: { minutes: 14, tokens: 67458 }
id: l-fd314be1
scope: repo
status: active
src: agent
touches:
  - TODO.md
---

## Decided
Grill session 2026-10-29, recorded as the "Terminal: *" rows and milestone M7 in `TODO.md`:

- New module `zircon.terminal`, targets jvm + linuxX64 + linuxArm64 + macosX64 + macosArm64 (mingwX64 later). Ships in **2.0.0**, not 2.1; the release waits for it.
- Cells are text only: CP437/TrueType tilesets emit the code point, terminal font draws it, tileset pixel size ignored (1 tile = 1 cell, mixed-size layers align by cell). Graphical tiles fail fast. Wide/zero-width chars become U+FFFD / skipped.
- Output: truecolor SGR only, double buffer + diff + SGR run coalescing, synchronized output `CSI ?2026h/l`, idle = 0 bytes. Grid fixed-size, centred + clipped, too-small banner. Real terminal cursor (CUP + ?25h/l + DECSCUSR).
- Input: kitty keyboard protocol **required** (no legacy xterm parser; unanswered `CSI ?u` query = fail fast). Mouse SGR 1006 + any-event 1003. Ctrl+C is a key event; `runTerminal(quitKeys = setOf(ctrl('c')))` default.
- Loop: blocking `runTerminal(tileGrid, targetFps = 60, beforeFrame)` on calling thread, `poll(stdin)` driven; alt screen; restore tty in `finally` + SIGTERM/SIGHUP; restore before rethrow.
- Tty layer: `expect`/`actual`; native = `platform.posix` termios/ioctl/poll; JVM = `stty` subprocess + `sun.misc.Signal` WINCH (Java 17, no JLine, no FFM).
- Tests: `Tty` interface + `FakeTty`, golden `.ansi` byte snapshots in commonTest, parser table tests. Manual smoke on foot/ghostty/alacritty/kitty/wezterm.
- Bar: 240x135 all-cells-change ≥ 30 fps on foot/ghostty/alacritty, idle 0 bytes, tick < 4 ms.
- Examples: `zircon.examples` becomes core-only (Compose glue in `Demos.kt` moves to desktop/web launchers), new `zircon.examples.terminal` launcher.

## Tried and ruled out
- Kitty graphics protocol / Sixel for graphical tilesets: per-terminal, slow, foot/alacritty lack kitty graphics. Text only.
- 256-colour fallback: all six target terminals speak truecolor; quantization code and detection heuristics not worth it.
- Legacy xterm key parsing: no key release, Esc timeout, ambiguous modifiers; user chose to require kitty protocol instead.
- Dynamic `TileGrid.resize`: big core change touching Compose too; grid stays fixed, terminal resize only redraws.
- JLine / Java 22 FFM for the JVM tty: heavy dependency / breaks Java 17 bytecode decision.
- Drawing the cursor as an inverted cell: fights the terminal's own cursor.

## Watch out
- bash/zsh/fish in the user's request are shells, not renderers; support list really means the terminal emulators (foot, ghostty, alacritty, kitty, wezterm) and their escape capabilities.
- `zircon.examples` currently has `api(project(":zircon.compose"))` and only jvm+wasmJs targets; only `Demos.kt` uses Compose. The split must land before the native terminal launcher can reuse the demos.
- `BaseRenderer` has a `//! TODO: FIX CURSOR HANDLING`; the terminal path uses the hardware cursor and does not need it, the Compose path still does.
- tmux needs `-T RGB` for truecolor and ≥ 3.4 for kitty keyboard passthrough; document, don't work around.
