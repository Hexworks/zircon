---
title: "Exception handling policy for 2.0 (grill 2026-10-30): contain per unit of work in core, onError hook in ZirconDefaults, renderers substitute placeholders, adds…"
author: Bruce
date: 2026-10-30
branch: master
cost: { minutes: 18, tokens: 22006 }
id: l-d090aa49
scope: repo
status: active
src: agent
---

## Decided
Grill 2026-10-30 on the exception audit (see the audit learning). Policy for Zircon 2.0:
- A throw escaping a UI listener, animation tick or tile draw never kills the app. The catch sits per unit of work in core: `DefaultUIEventProcessor` per listener call (treated as Pass), `DefaultAnimationRunner` per animation (that animation stops), `BaseRenderer` per tile draw. Other listeners and the rest of the frame still run. No per-frame catch in the renderers.
- A listener that threw stays subscribed and is logged every time; spam from a MOUSE_MOVED handler is the signal to fix it.
- Reporting: core logs through cobalt's LoggerFactory and also fires a process-wide `onError(source, throwable)` hook configured in `ZirconDefaults` (not per grid). The terminal launcher routes it to stderr because logging to stdout corrupts the raw-mode screen; the examples app shows the last error in a footer line next to the FPS counter.
- Bounds checks: `moveTo` returns false instead of throwing (it already returns Boolean). `addComponent` out of bounds/overlap, HBox/VBox full, LogArea element too tall, `checkCompatibilityWith` (cell size mismatch) keep their require(): construction bugs stay loud. Negative `Size` and negative component position keep throwing; no clamping.
- Unknown glyphs: every tileset draws a visible placeholder and never throws ('?' for CP437, U+FFFD in the terminal, a marked cell for an unknown graphical sprite name). `Char.CP437Index()` becomes internal; the public API is the OrNull variant.
- Tiles a renderer cannot draw (graphical tile in the terminal, tile type with no factory): the renderer substitutes a placeholder tileset and logs once per resource, instead of `TextTileset`/`TilesetRegistry`/`DefaultTilesetLoader` throwing.
- Harden only user-reachable lookups: focus order falls back to root when the focused component vanished, tab bar ignores an unknown key with a log, animation runner tolerates a concurrently removed animation. Pure invariants (`getLayerAtOrNull(0)!!`, `!!` after a null check) stay.
- Colour channels clamp to 0..255 in `DefaultColor` on construction, one place for every producer.
- Shipped as four commits in dependency order: 1) core safety net + onError with tests; 2) renderer substitutions; 3) moveTo result, lookup hardening, DefaultColor clamp, `TileGraphicsBuilder.tiles` setter and `SetiUiGlyphs` TODO fixes; 4) examples fixes (MoveExample, HBoxes/VBoxes full, ScrollingExample valueOf) + status line.

## Tried and ruled out
Per-frame catch in `TileGridView`/`RunTerminal`: a throw would abort the whole frame, dropping later listeners and leaving a partial draw. Unsubscribing a failing listener: hides a half-broken UI. Making addComponent return a result: too large an API change for 2.0 and hides construction bugs. Per-grid onError in TileGridConfig: a library-wide sink (Sentry etc.) would have nowhere to go. Clamping negative Size to zero: silently collapses layouts.

## Watch out
`ZirconDefaults` is process-wide by the AppConfig split decision; the onError hook follows that split on purpose, so tests that install a hook must restore the previous one.
