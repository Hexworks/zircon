---
title: "The text area scrolls against its content area and keeps the cursor in view after any move, backspace joins lines, and the scroll bar thumb and clicks map the…"
author: Bruce
date: 2026-11-17
branch: master
cost: { minutes: 8, tokens: 71666 }
id: l-a4fee5b4
scope: repo
status: active
src: agent
---

## Decided
2026-11-17, refactor 3 of the component handover, tests first. Findings the code did not show:
- `DefaultTextArea` delegated `Scrollable` with `visibleSize` = the whole component size. With a box the cursor could sit under the frame and be counted as visible, so typing at the end of a line hid it instead of scrolling. The text area now implements `Scrollable` itself with `visibleSize` reading `contentSize` (it follows theme frames too).
- The old `refreshCursorAndScroll` only handled one-cell moves and ran only when attached to a root; a click, a new `text`, or a joined line could leave the cursor outside the view. One `scrollCursorIntoView(deleting)` computes the offset that shows the cursor and runs after keys, clicks, focus and text changes, with no root needed. While deleting and scrolled right it keeps the cursor at the right edge (the old special case, generalised).
- `DeleteBeforeCursor` at the start of a line removed the line and lost its text; it now joins the line onto the previous one with the cursor where they met.
- `DefaultVerticalScrollBarRenderer` sized the thumb from the bar's height standing in for the visible height, used `actual + bar - offset` as the range and hid the thumb at offset 0. Now: thumb = bar × visible / actual (min 1), position = offset / (actual − visible) × (bar − thumb), no thumb when everything is visible.
- `DefaultVerticalScrollBar.scrollToMouse` mapped a click to `actual × y / barHeight`, so the lower third clamped to the end; it maps the first content row to 0 and the last to `actual − visible`. The builder sets `preferredContentSize`, so a decorated bar grows by its decorations instead of losing its content rows.

## Tried and ruled out
- Keeping `Scrollable.create(size, size)` and resizing it on theme changes: `DefaultScrollable.visibleSize` is a `val`; a live getter on the component is simpler.

## Watch out
- `DefaultTextAreaTest` is `@Ignore`d with TODO assertions; `TextAreaScrollingTest` covers editing and scrolling, not styles.
- `Input` has its own one-line editor (not `TextEditor`) and already measured against `contentSize`.

In the code:
- `zircon.core/.../internal/component/impl/DefaultTextArea.kt` (`visibleSize`, `scrollCursorIntoView`, `textChanged`), `texteditor/transformation/DeleteBeforeCursor.kt`.
- `internal/component/impl/DefaultVerticalScrollBar.kt` (`scrollToMouse`), `internal/component/renderer/DefaultVerticalScrollBarRenderer.kt`, `api/builder/component/VerticalScrollBarBuilder.kt`.
- Tests: `TextAreaScrollingTest`, `DefaultVerticalScrollBarTest`.
