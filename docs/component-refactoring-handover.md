# Component Refactoring Handover

Follow-up sessions after component themes ([component-themes.md](component-themes.md)) landed on 2026-11-14. Each item
is one session. Do them in order; 1 touches the public API the most and the theme's `CheckBox` roles depend on it.

**Order change (2026-11-16):** the four palette sessions in [component-palettes.md](component-palettes.md) (role split,
default theme, palette, bundled palettes) run *before* these. After them `ComponentRole` holds kinds only and
`Variant` / `Part` exist, so the role notes below read as `role` + `variant` + `part`.

## Context to load first

- `docs/component-themes.md`: the theme model. Every component has a `ComponentRole`; themes map roles to
  `ThemeEntry`s. New or merged components must keep a role per *semantic* kind so themes keep working.
- `zircon.core/.../api/component/theme/ComponentRole.kt`: the role enum. Merging components must not remove roles
  that the Neobrutalism theme maps (`CHECK_BOX`, `RADIO_BUTTON`, `TOGGLE_BUTTON` stay as roles even when one
  component implements them).
- `zircon.core/.../internal/component/impl/DefaultComponent.kt`: renderer is mutable, `contentSize` is derived.
- Tests: `zircon.core/src/commonTest/.../component/` and the examples app (`zircon.examples`), which has a
  *Component themes* page that must keep building.

## 1. BinarySelector

Collapse `CheckBox`, `RadioButton`, `ToggleButton` into one component.

- The three differ only in state enums (`CHECKING/CHECKED/UNCHECKING/UNCHECKED` vs `PRESSED/SELECTED/NOT_SELECTED` vs
  plain `Selectable`) and renderers. The states are only read by renderers and can be derived from `isSelected` +
  `ComponentState`.
- New `BinarySelector : Selectable` with `allowUnselect: Boolean` (`false` for radio behavior).
- Keep `checkBox { }`, `radioButton { }`, `toggleButton { }` builder functions; they parameterize `BinarySelector`
  with the matching role and renderer.
- `RadioButtonGroup` → `ExclusiveSelectionGroup`: accepts any `Selectable`, keeps exactly one selected.
- `Selectable` gains `key: String`, unique within a group. Default key is derived from the normalized text; building
  fails when no key is set and none can be derived.
- Migration: keep the old interfaces as typealiases or thin subtypes for one release if cheap, else document the break.

## 2. HBox / VBox dedupe

`DefaultHBox` and `DefaultVBox` are copies that differ in `addComponent` and `reorganizeComponents`. Extract a shared
`DefaultLinearBox` (or similar) parameterized by axis. Internal only, no public API change. Check the examples that
reorganize on detach still pass.

## 3. TextArea and ScrollBar fixes

- `TextArea`: the cursor offset bug with decorations was fixed in the theme session (`DefaultTextAreaRenderer` and
  `DefaultTextArea.mouseReleased` now use `absolutePosition + contentOffset`); scrolling and typing still have issues.
  Write failing tests first (scroll on overflow, type at end of line, backspace across a wrapped line).
- `ScrollBar`: same class of offset problems. Audit `DefaultVerticalScrollBar` against decorations.
- The new single-line `Input` component shares the text editor; a fix in one likely applies to the other.

## Rules that held during the theme session

- Commits: subject line only, no body, no AI footers (see memory `commit-style`).
- Run `trail check` before committing; save a `trail note` for decisions the code cannot show.
- New feature ideas go to `ROADMAP.md` as short outlines.
