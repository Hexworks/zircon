---
title: "Word wrap has one implementation: fragments measure height with the same line breaker that lays the tiles out"
author: Bruce
date: 2026-11-16
branch: master
cost: { minutes: 9, tokens: 232426 }
id: l-ce456f3b
scope: repo
status: active
src: agent
touches:
  - ROADMAP.md
  - docs/component-refactoring-handover.md
  - docs/component-themes.md
  - docs/neo-brutalism.md
  - docs/research/semantic-component-palettes.md
  - zircon.core/api/zircon.core.api
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/graphics/BoxType.kt
  - zircon.examples/src/commonMain/kotlin/org/hexworks/zircon/examples/components/DecorationsExample.kt
---

## Decided
2026-11-16, review of the component-themes diff. Text was word-wrapped twice: `DefaultCharacterTileString` laid tiles out with a cursor-walking algorithm from 1.x, and the theme fragments (Alert, Card, CheckBoxField, Window) counted lines with a separate string-based `wrapWords` to size themselves before building. The two disagreed, so a fragment could compute N rows and the renderer draw N+1, clipping the last line. Now a single generic `wrapWords<T>(items, width, charOf)` in `internal/graphics` does both: the string class maps its lines to tile positions row by row, the fragments call the `String` overload / `linesFor`.

Rules of the one breaker: a word that does not fit moves whole to the next line and the separator before it is dropped; separators inside a line (and leading ones) are kept as the original tiles; a word longer than the width starts on a fresh line and is chopped; `\n` always breaks.

## Tried and ruled out
Keeping the 1.x tile algorithm and only adjusting the measuring helper to mimic it: the 1.x one kept a space tile that could land in column 0 of a wrapped row and push the next word down again, and it drew `\n` as a glyph instead of breaking. Mimicking those is reproducing bugs. `Paragraph` now honours `\n`; the existing `DefaultCharacterTileStringTest` cases still pass.

## Watch out
Anything that sizes itself from text must go through `linesFor`/`wrapWords`, never its own split. `WordWrapTest.the_drawn_rows_match_lines_for` pins measure == draw for widths 1..20; extend it if the rules change. `CharacterTileIterator` was deleted with the old algorithm (it was public in an internal package; `apiDump` regenerated).

In the code:
- `zircon.core/src/commonMain/kotlin/org/hexworks/zircon/internal/graphics/WordWrap.kt`
- `DefaultCharacterTileString.buildTiles()` WORD_WRAP branch
- callers: `DefaultAlert`, `DefaultCard`, `DefaultCheckBoxField`, `DefaultWindow`
- tests: `zircon.core/src/commonTest/.../internal/graphics/WordWrapTest.kt`
