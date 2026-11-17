---
title: "A terminal without the kitty keyboard protocol (tmux, screen, xterm) runs on legacy keys with a synthesized release per press instead of aborting"
author: Bruce
date: 2026-11-17
branch: master
cost: { minutes: 34, tokens: 200761 }
id: l-8de5a32d
scope: repo
status: active
replaces: l-298d125d
src: agent
touches:
  - README.md
  - ROADMAP.md
  - docs/component-palettes.md
  - docs/system-theme-handover.md
  - zircon.core/api/zircon.core.api
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/Palettes.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/component/theme/PaletteStyles.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/internal/component/theme/AnsiPalettes.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/internal/component/theme/BundledPalettes.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/internal/component/theme/ColorContrast.kt
---

## Decided
2026-11-17, on the user's question whether a tmux fallback would be hard: not hard, half a session. The terminal renderer no longer aborts when the kitty keyboard query goes unanswered (tmux never answers it, no version does; see the Oct 30 learning this replaces). It keeps running with the terminal's legacy key encoding and logs a warning that prints once the terminal is restored; `runTerminal(requireKittyKeyboard = true)` brings the fail-fast back for an app that needs real releases or chords. The TODO.md "Terminal: input" row changed from "required" to "preferred" accordingly.

The one thing that would have silently broken: core acts on `KEY_RELEASED` for Tab focus navigation (`NAV_NEXT` / `NAV_PREV`), the default `ShortcutsConfig` type, and `BaseNumberInput`. A terminal without the protocol never sends releases, so the parser's legacy mode appends a `KEY_RELEASED` copy after every `KEY_PRESSED` (press, typed, released: the same triplet `injectStringAsKeyboardEvents` emits). Verified end to end in a detached tmux 3.6a (`tmux -L zt new-session -d ... kexe`, `send-keys`, `capture-pane -e`): Tab cycles the focus, Space opens the dialog, SGR mouse clicks switch tabs, Ctrl+C exits with the warning and `legacy keys` in the fps line, 44-58 fps.

Also added: `CSI Z` (Shift+Tab in the legacy encoding) which the parser had no case for, so `NAV_PREV` now works on every terminal.

The kitty push `CSI > 31 u` still goes out in legacy mode: tmux with `extended-keys on` forwards modified keys in the CSI u form on seeing it (the parser reads them like protocol keys), every other terminal ignores the sequence.

## Tried and ruled out
- Treating the missing reply as fatal (the 2.0 design row): fine for foot/ghostty/kitty, but the user's own tmux crashed on it, and screen, xterm, editor terminals and mosh were all out.
- Skipping the synthesized release and letting apps cope: Tab focus and number inputs would be dead on legacy keys with no error anywhere.
- Enter to activate a focused button: core's activation shortcut is Space (`ShortcutsConfigBuilder`), Enter does nothing; the module README said "Enter/Space" and now says Space.

## Watch out
- Legacy keys mean: no repeats or releases from the terminal (only the synthesized one), Esc and Alt+key look alike (a lone ESC at the end of a read is Escape, ESC + byte is Alt), Ctrl+I / Ctrl+M / Ctrl+H arrive as Tab / Enter / Backspace, modifiers only where the encoding carries them (`CSI 1;5C` style). Nothing else degrades: SGR mouse, hover and sync output pass through tmux 3.2+.
- tmux downsamples truecolor to 256 colors unless the outer terminal is declared truecolor (`terminal-overrides ",*:Tc"` or `RGB`); it still never answers the OSC color queries, so `System` there is the bundled family in the OS light/dark.
- Esc does not close the examples' modal dialog in any terminal; that is the component, not the key path.

In the code: `zircon.terminal/.../RunTerminal.kt` `handshake()` (`parser.legacyKeys = true` + `logger.warn`), `TerminalSession.hasKittyKeyboard`, `runTerminal(requireKittyKeyboard)`; `input/InputParser.kt` `legacyKeys`, `addKey`, the `'Z'` case in `handleCsi`; tests `runsOnLegacyKeysWithoutTheKittyProtocol…`, `failsFastWithoutTheKittyProtocolWhenTheApplicationRequiresIt`, `legacyKeysSynthesizeTheReleaseAfterEveryPress`; checklist item in `zircon.terminal/README.md`; terminal api dump regenerated.
