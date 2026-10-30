---
title: "Examples app 2026-10-30: packages horizontal, examples vertical, scrolling tab bars in core, min grid 100x40, lazy example build with shown/hidden hooks"
author: Bruce
date: 2026-10-30
branch: master
cost: { minutes: 33, tokens: 305162 }
id: l-40bdb3c8
scope: repo
status: active
src: agent
touches:
  - ".github/workflows/PUBLISH.yml"
  - ".github/workflows/TEST.yml"
  - Makefile
  - README.md
  - TODO.md
  - build.gradle.kts
  - settings.gradle.kts
  - zircon.examples.compose/build.gradle.kts
  - zircon.examples.compose/src/commonMain/kotlin/org/hexworks/zircon/examples/compose/ExamplesApp.kt
  - zircon.examples.compose/src/jvmTest/kotlin/org/hexworks/zircon/examples/compose/ExamplesAppTest.kt
---

## Decided
Grill 2026-10-30 for porting the 1.x example suite (`zircon_old/.../zircon.jvm.examples`):

- Layout: packages (animations, components, dialogs, fragments, game, modifiers, other, input+layers) are the **horizontal** tab bar, examples of a package the **vertical** one (tabWidth 16), theme + tileset selectors in a 3-row header. `zircon.examples/.../ExamplesScreen.kt`.
- Tab bars scroll in core (`internal/fragment/impl/TabStrip.kt`, used by both `Default*TabBar`): `◄..`/`..►` and `▲...`/`▼...` box buttons appear only while tabs lie beyond that end (mockup from the user), wheel over the strip scrolls one tab, `selectedKey` scrolls into view. Own first-visible index, not `Scrollable`. The builders no longer `require` that all tabs fit; they now also apply `position` (was ignored before).
- Min grid 100x40 (`MIN_SIZE`); examples lay out to `context.contentSize` (≥ 84x31). Terminal launcher prints a message and exits below that.
- Examples are built lazily on first show; `ExampleView.layers` are added/removed by the app, `onShown`/`onHidden` start/stop animations and dispose key/mouse subscriptions, `onFrame` runs only for the visible example. Example tab names ≤ 13 chars. `Example.textOnly = false` hides it in the terminal.
- Tileset selector hidden in the terminal; in Compose a tileset change rebuilds the grid at the new cell size (`ExamplesApp` keeps the latest `ExamplesSelection`). Theme changes apply live via `screen.theme`.
- Old 2.0 demos folded in: Skull + Logo → animations, Sprites → game, Benchmark → other; the old Components demo dropped.
- Skipped ports (2.0 cannot do them): modifiers/Borders and components/Borders (Border modifier gone), CustomTile (sealed `Tile`), ImageTile (image dictionary tilesets not in 2.0), UpdateOnAttach (flag gone), SavingScreenshots, WindowTitle (platform features).

## Tried and ruled out
- Packages vertical / examples horizontal (first proposal): `components` has 30 examples, a horizontal row holds ~7. User flipped the axes and asked for scrolling instead of splitting packages or merging examples.
- `TabBar : Scrollable`: cell-based offset for 3-cell tabs adds a leaky unit; examples-only wrapper would leave the "must fit" restriction in core.
- Building every example up front: hidden examples with layers/animations would draw over the visible one and the Benchmark's per-frame fill would tax every tab.

## Watch out
- Colour-only art (the skull is background colour on spaces) is invisible in a plain text dump of the terminal output; the pty harness `vt_dump.py` in the scratchpad shades non-default backgrounds. `TerminalExamplesTest` counts those cells as `#`.
- Core had leftover debug `println`s in `HBoxBuilder` and `DefaultSelector`; removed. Watch for more when a tty shows stray text.
- `zircon.examples.terminal` now has a jvmTest that renders every text-only example through `TerminalRenderer`; `zircon.examples.compose`'s jvmTest renders each package in a full HD scene and selects every example once.
