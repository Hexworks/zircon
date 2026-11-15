---
title: "Component themes (grill 2026-11-14): themes are role-to-entry data propagated like the color theme, the screen owns the theme, switching keeps every component'…"
author: Bruce
date: 2026-11-14
branch: master
cost: { minutes: 92, tokens: 753411 }
id: l-56ccf387
scope: repo
status: active
src: agent
touches:
  - docs/component-themes.md
  - docs/component-refactoring-handover.md
---

## Decided
Grill 2026-11-14 for the component theme feature (docs/component-themes.md). A ComponentTheme is data: a map from ComponentRole to a ThemeEntry (renderer, decorations, post processors, style set) plus a palette. One generic ComponentFactory serves every theme; no per-theme builder factory. The theme lives on the Screen as a property and propagates parent to child on attach and on change, the way the ColorTheme does; a null parent theme does not clear a theme set explicitly on a child, so a themed subtree can live in an unthemed screen (the examples app does this). A component theme wins over the ColorTheme only while it has an entry for the component's role; without one the component keeps the look it was built with and follows the ColorTheme. A switch never moves anything: outer size stays, content size is recomputed from the new decorations and clamped at zero, containers shift children by the content offset delta, and decorations that would leave no content cell are skipped (renderer and colors still apply). Explicitly set renderer, decorations or style set on a builder are pinned and survive switches. The factory runs the user's block first and applies the entry when the metadata is created, so `role = BUTTON_PRIMARY` inside the block counts; a themed builder presets its theme on the children built in its block, so nested `button { }` calls are themed too. ThemeMode.SYSTEM resolves through ZirconDefaults.systemThemeDetector, an expect/actual per platform (macOS defaults, gsettings, Windows registry, prefers-color-scheme), DARK when unknown. Neobrutalism is the first theme, in core.

## Tried and ruled out
- Factory keeping references to every component it built and iterating them on a switch (the original plan): leaks, misses components built with the plain DSL, breaks on detach and reattach. Propagation through the component tree covers all of that for free.
- Abstract factory per theme with its own composite fragments: N themes times M components of code, and a switch between themes with different composite structure is undefined. Composites (Alert, Badge, Card, Window, the field fragments) are generic core fragments built from roles instead.
- Resizing components on a switch and re-laying out parents: needs resizable components and breaks absolute positions.
- Applying the theme entry before the user's builder block: `role = X` set in the block was ignored and seeded decorations from the default role stuck around (bit the tab bar's inner panels).
- A box decoration plus a separate shadow decoration for the pressed button: the hover shift needs to move the already drawn content, so the frame and shadow are one decoration that moves the content cells itself.

## Watch out
- Zircon's `fill` only touches empty cells: a decoration that wants padding to carry the hover background must fill empty interior cells itself (NeoFrameDecoration.fillEmptyCells); a test that prefills a graphics with `_` will hide that.
- A component's own `render(graphics)` draws only that component; children are separate renderables. The theme tests compose the tree themselves (`renderToRows` in ComponentThemeTest).
- The contentSize clamp to zero is deliberate here and differs from the construction-time rule (a learning ruled out clamping at construction): on a runtime switch the alternative is throwing from a property change. The builder path still throws for a preferredSize smaller than the decorations.
- CP437 sheets lack the heavy box-drawing glyphs and the eighth/quadrant blocks Neobrutalism draws; the examples app has no bundled Unicode font yet, so the page shows `?` until a TrueType tileset is picked (ROADMAP lists bundling a Nerd Font).
- The examples package tab strip is exactly full at the minimum width; a ninth group makes it scroll and breaks PackageTabClickTest, so new pages go into existing groups.
- `ExamplesScreenTest.aRebuiltScreenKeepsTheSelection` and `aTilesetChangeReachesTheGridAndTheComponents` fail on the clean tree on this machine too (a `first {}` lookup of a theme or tileset), unrelated to this change.
- Inside a builder block, names resolve to the enclosing fragment's members first: `isSelected = x` in `checkBox { }` hit the fragment's own property before its check box existed (NPE).

In the code:
- Theme API: zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/component/theme/ (ComponentTheme, ComponentRole, ThemeEntry, ThemeMode, SystemThemeDetector, ComponentFactory, PinnedAspects, NeobrutalismBuilder/Palette), ComponentThemes object in api/.
- Application: DefaultComponent.applyComponentTheme, DefaultContainer (inheritComponentTheme on attach), BaseComponentBuilder.applyThemeEntry/presetTheme, Dsls.buildChildFor.
- Neobrutalism: internal/component/theme/neobrutalism/.
- New: Input component, fragments Alert/Badge/Card/Window/CheckBoxField/InputField/ProgressField/TextAreaField, ThemedFragmentBuilder.
- Follow-ups for the refactors: docs/component-refactoring-handover.md.
