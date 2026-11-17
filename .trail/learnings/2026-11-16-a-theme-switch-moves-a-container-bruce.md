---
title: "A theme switch moves a container's children by the content offset without the bounds check, and the matrix tab bar carries the same roles and orientation as th…"
author: Bruce
date: 2026-11-16
branch: master
cost: { minutes: 21, tokens: 91723 }
id: l-57b67f68
scope: repo
status: active
src: agent
touches:
  - README.md
  - ROADMAP.md
  - docs/component-palettes.md
  - zircon.core/api/zircon.core.api
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/Palettes.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/component/theme/BundledPaletteVariants.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/internal/component/theme/BundledPalettes.kt
  - zircon.core/src/commonTest/kotlin/org/hexworks/zircon/api/PalettesTest.kt
  - zircon.examples/src/commonMain/kotlin/org/hexworks/zircon/examples/Catalog.kt
  - zircon.examples/src/commonMain/kotlin/org/hexworks/zircon/examples/ExamplesScreen.kt
---

## Decided
2026-11-16, after the first app-wide component theme selector in the examples. Two causes behind "misaligned after switching back", "vertical tabs look horizontal" and "empty content after re-selecting a tab":

1. `DefaultContainer.onContentOffsetChanged` moved children with `moveTo`, which refuses a move whose bounds leave the parent. Under a theme that frames the container, a child sized for the old content area cannot move, stays put, and on the switch back is moved by the negative delta anyway: one cell off for good. Children now move with an unchecked `shiftBy(delta)` (`InternalComponent`), overflowing while the heavy theme is on; the plan already says "size components for the heaviest theme".
2. `DefaultMatrixTabBar` was built with plain `panel`/`hBox`/`vBox` roles and pinned renderers (session C only did the single tab bars). Under Neobrutalism its root and content panel got the panel frame (a frame around the whole app, demos that no longer fit the shrunken content panel and failed to attach), and its vertical tabs were drawn horizontally. It now mirrors the single bars: root `NONE`, strips `TAB_BAR` with their orientation, content `TAB_BAR#CONTENT`, vertical tabs built with `vertical = true`, nothing pinned. Its strips lost their one-cell indent like the single bars did in session A; the capacities grew by 2.

## Tried and ruled out
- Keeping the matrix strips' pinned margins so the indent survives: pinned decorations also block a theme's strip look, and the indent cannot be expressed per orientation in a theme entry anyway.

## Watch out
- `shiftBy` does not validate; a theme switch can leave children outside their parent until the switch back. Rendering clips, adding new children checks bounds against the shrunken content area.
- The examples test `everyExampleSurvivesTheNeobrutalismThemeAndBackInTheSmallestGrid` flips every demo under both themes and asserts the shown content stays attached; keep it when adding themes.

In the code:
- `zircon.core/.../internal/component/InternalComponent.kt` (`shiftBy`), `internal/component/impl/DefaultComponent.kt`, `DefaultContainer.kt` (`onContentOffsetChanged`)
- `internal/fragment/impl/DefaultMatrixTabBar.kt`, `api/builder/fragment/MatrixTabBarBuilder.kt` (`build(width, vertical)`)
- tests: `ComponentThemeTest.a_child_that_no_longer_fits_under_a_theme_still_moves_with_the_content_and_back`, `DefaultMatrixTabBarTest.stripsLieAroundTheContent`, `zircon.examples/src/jvmTest/.../ExamplesScreenTest.kt`
