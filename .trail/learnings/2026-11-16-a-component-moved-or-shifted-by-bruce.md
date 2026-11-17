---
title: "A component moved or shifted by its container always carries its whole subtree unchecked, and layout-only holders in the examples get role NONE so a theme neve…"
author: Bruce
date: 2026-11-16
branch: master
cost: { minutes: 19, tokens: 156036 }
id: l-9bb5b8cc
scope: repo
status: active
replaces: l-57b67f68
src: agent
---

## Decided
2026-11-16/17, after the app-wide component theme selector in the examples still broke on switching back and forth. A theme switch may leave a child overflowing its parent (sized for the stock look, now under a frame); that is accepted. What must never happen is a child drifting from its parent. So:
- `DefaultContainer.onContentOffsetChanged` and `DefaultContainer.moveTo` move children with the unchecked `InternalComponent.shiftBy`, never the checked `moveTo` (which refuses a move whose bounds leave the parent and silently returns false). A container's own move is checked against its parent; its children just follow.
- `shiftBy` cascades: `DefaultContainer` overrides it to shift its children too. A non-cascading shift left grandchildren behind (the "all components" demo drawn at the screen origin while its placeholder sat framed at (16,6)).
- The examples' per-cell placeholder panels are `role = ComponentRole.NONE`: a plain `panel` is kind PANEL, Neobrutalism frames it, and an example built lazily while that theme is active no longer fits ("Adding out of bounds component ... is not allowed", which also cancelled the tab bar's subscription so nothing rendered afterwards). The attach now sits inside the build try, so a misfit lands in `buildFailures` instead of killing the subscription.
- The matrix tab bar mirrors the single bars' roles: root NONE, strips TAB_BAR with orientation (vertical tabs built `vertical = true`), content TAB_BAR#CONTENT, nothing pinned; strips are flush like the single bars.

## Tried and ruled out
- Keeping the matrix strips' pinned margins so the indent survives: pinned decorations also block a theme's strip look.
- Fixing only the placeholder role: the desync between a shifted container and its children reappears for any framed panel holding a full-size child.

## Watch out
- `shiftBy` validates nothing; after a heavy theme a child can sit outside its parent until the theme is switched back. Rendering clips, `addComponent` does not: adding into a shrunken content area throws.
- An example opened for the first time under a non-default theme is built with the default theme and re-themed on attach; a test must visit cells under Neobrutalism on a fresh screen to cover that path.
- The examples JVM test task has `maxHeapSize = "1g"`: the screen tests build every example several times and the 512m default ran out once a third full walk was added. Screens may be retained across tests (global buses/defaults); not investigated.

In the code:
- `zircon.core/.../internal/component/impl/DefaultContainer.kt`: `moveTo` (children `shiftBy(diff)`, returns false when its own move is refused), `shiftBy` override, `onContentOffsetChanged`.
- `zircon.core/.../internal/component/impl/DefaultComponent.kt`: `moveTo` keeps the parent-bounds check; `shiftBy` is open.
- `zircon.core/.../internal/fragment/impl/DefaultMatrixTabBar.kt` roles; `MatrixTabBarBuilder.build(width, vertical)`.
- `zircon.examples/.../ExamplesScreen.kt`: placeholder `role = ComponentRole.NONE`, attach inside the try in `show`.
- Tests: `ComponentThemeTest.an_overflowing_child_follows_its_container_when_it_is_detached_and_reattached`, `a_child_that_no_longer_fits...`, `ExamplesScreenTest.anExampleFirstOpenedUnderTheNeobrutalismThemeFitsAndSitsAtTheContentOrigin`.
