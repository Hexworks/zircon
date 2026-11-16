---
title: "Theme lookup falls back from a part to the component kind, never to the composite's own entry; role NONE has no fallbacks"
author: Bruce
date: 2026-11-16
branch: master
cost: { minutes: 15, tokens: 156431 }
id: l-df9924ed
scope: repo
status: active
src: agent
touches:
  - ROADMAP.md
  - docs/component-palettes.md
  - docs/component-refactoring-handover.md
  - docs/component-themes.md
---

## Decided
Session C of the palette plan (2026-11-16). A component carries `kind` (what the builder made, e.g. LABEL) next to `role` (the composite it belongs to, e.g. CARD), `variant` and `part`. `ComponentTheme.resolveEntry` tries (role, variant, part), (role, DEFAULT, part), (kind, variant), (kind, DEFAULT). `ComponentRole.NONE` resolves to nothing even when the kind has an entry.

Tabs: the unselected RadioButton and the selected Label both have role TAB and the bar's `orientation`; one `NeoTabRenderer` draws both (a non-Selectable component is the selected one) and reads orientation from the component. `Orientation` is a new component property set by the vertical slider/number input/scroll bar builders and the tab fragments.

## Tried and ruled out
The grill's "part, then variant" order, (role, variant, part) -> (role, variant, NONE): the NONE key is the composite container's own entry, so a card title would have inherited the card's frame. Falling back to the kind instead gives a title the plain label look.
Letting role NONE fall back to the kind: `role = NONE` is how layout-only panels (tab roots, test content) opt out of theming; the first run framed every one of them.

## Watch out
Themes must define entries for every part whose look must differ from the plain kind; a missing part entry silently becomes the kind's look (not an error).
`lacks(role, part, kind)` in fragments must pass the kind, or the legacy-look check misses kind-only themes.

In the code:
- `zircon.core/.../api/component/theme/ThemeKey.kt` (`fallbacks(kind)`), `ComponentTheme.kt` (`resolveEntry`), `ComponentMetadata.kind`, `api/component/data/Orientation.kt`
- tests: `ComponentThemeTest` (`a_part_without_an_entry_falls_back_to_the_component_kind_not_to_its_composite`, `the_fallback_order_is_default_variant_then_kind`)
