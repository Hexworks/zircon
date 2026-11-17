---
title: "The terminal renderer adopts the terminal's own colors by querying OSC 10, 11 and 4 and re-reads them on a mode 2031 theme report or when a background poll cha…"
author: Bruce
date: 2026-11-17
branch: master
cost: { minutes: 19, tokens: 123540 }
id: l-6988faae
scope: repo
status: active
src: agent
touches:
  - zircon.examples.terminal/src/commonMain/kotlin/main.kt
---

## Decided
2026-11-17, after the user switched their terminal's theme and nothing happened. The desktop `SystemThemeDetector` reads the OS setting (gsettings, defaults, registry), which a terminal theme switch does not touch, and nothing in `zircon.terminal` knew about colors at all. Options weighed with the user: light/dark only (OSC 11 luminance), adopting the terminal palette (chosen), or indexed ANSI output the way mc does it (mc never detects anything: its skins use the 16 slots and `default`, the terminal recolors them; ruled out, the renderer is truecolor only).
- `TerminalSession` sends `OSC 10;?`, `OSC 11;?` and sixteen `OSC 4;n;?` in the handshake before `CSI ?u CSI c`; the DA reply closes the set, unanswered slots take the xterm defaults, and `onTerminalColors(TerminalColors)` fires before the first frame. Mode 2031 is enabled with the other modes; a `CSI ?997;1|2 n` report re-queries everything. Terminals without 2031 are covered by an `OSC 11;?` poll every two seconds that re-queries when the background answer differs.
- `InputParser` now understands `OSC … BEL|ST` (color replies to the listener, no events; waits for a split terminator, gives up past 4 KiB) and the 997 report; before, `ESC ]` was read as Alt+`]`.
- Core maps the 18 colors with `Palettes.fromAnsi`: surfaces are steps from background towards foreground (a terminal UI is flat), blue is primary and focus border, cyan accent/info, green/yellow/red statuses, bright black is muted text unless it is too close to the background, every `ON_*` is whichever of foreground/background contrasts (WCAG luminance).
- Examples: `useTerminalPalette` puts the palette first in the selector's live `valuesProperty` (replacing the previous `Terminal`) and selects it; the terminal launcher passes it as `onTerminalColors`. `DefaultSelector` now follows a selection made outside it and a changed value list (its label only followed its own arrows before).

## Tried and ruled out
- Querying the colors before building the screen: the tty is only in raw mode inside `runTerminal`, so the app gets them through the callback and swaps palettes live instead.
- Carrying the colors through `SystemThemeDetector`: it yields light/dark only; the terminal gives real colors.

## Watch out
- tmux answers neither the color queries nor the kitty query (earlier learning): the callback never fires there.
- `onTerminalColors` runs on the loop thread; apply palette changes there (the examples do it in the callback itself).
- In the terminal the first frame already uses the terminal palette; the bundled default only shows when the terminal does not answer.

In the code:
- `zircon.terminal/.../TerminalColors.kt`, `RunTerminal.kt` (`colorQueries`, `queryColors`, `publishColors`, `COLOR_POLL_MS`), `input/InputParser.kt` (`parseOsc`, `parseColorSpec`, `ReplyListener.onColor/onColorScheme`), `AnsiEncoder.OSC/ST`.
- `zircon.core/.../api/Palettes.kt#fromAnsi`, `internal/component/theme/AnsiPalettes.kt`, `internal/fragment/impl/DefaultSelector.kt`.
- `zircon.examples/.../ExamplesScreen.kt#useTerminalPalette`, `zircon.examples.terminal/src/commonMain/kotlin/main.kt`.
- Tests: `ColorReplyTest`, `TerminalSessionTest.readsTheTerminalColorsAtStartupAndAgainWhenTheSchemeChanges`, `AnsiPaletteTest`, `DefaultSelectorTest`.
