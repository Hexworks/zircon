---
title: "Component themes will split shape (renderers, decorations) from color (semantic palette) so one look works with any color scheme"
author: Bruce
date: 2026-11-14
branch: master
cost: { minutes: 6, tokens: 70862 }
id: l-5e26b2fc
scope: repo
status: active
src: agent
touches:
  - docs/component-themes.md
---

## Decided
Right after component themes landed (2026-11-14) the user flagged that a theme bundling renderers *and* colors (Neobrutalism carries its own palette) forces copying the whole look for every color scheme they want (Catppuccin, Tokyo Night, Kanagawa). Direction: component parts get semantic color keys (primary, secondary, surface, warning, ...) and a separate `ComponentPalette` supplies the color per key; the theme keeps only shape (renderers, decorations, post processors). Research on how mc, lazygit, DaisyUI, shadcn/TweakCN, Textual, base16 do this was started before designing it.

## Tried and ruled out
Nothing yet; the current `ThemeEntry.componentStyleSet` per role is the thing to replace, not extend.

## Watch out
Do not add more hard-coded palettes to `NeobrutalismPalette`; that is the coupling being removed. Read the research doc before changing the theme model.

In the code:
- current model: `docs/component-themes.md`, `zircon.core/.../api/component/theme/ThemeEntry.kt`, `NeobrutalismPalette.kt`, `NeobrutalismTheme.kt`
- research output: `docs/research/semantic-component-palettes.md`
