---
title: "Check box, radio button and toggle button are one BinarySelector told apart by role and allowUnselect; the renderers read isPressed and isSelected and the excl…"
author: Bruce
date: 2026-11-17
branch: master
cost: { minutes: 19, tokens: 286139 }
id: l-18fd18c8
scope: repo
status: active
src: agent
touches:
  - README.md
  - zircon.core/api/zircon.core.api
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/builder/fragment/MatrixTabBarBuilder.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/internal/component/InternalComponent.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/internal/component/impl/DefaultComponent.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/internal/component/impl/DefaultContainer.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/internal/fragment/impl/DefaultMatrixTabBar.kt
  - zircon.core/src/commonTest/kotlin/org/hexworks/zircon/api/component/theme/ComponentThemeTest.kt
  - zircon.core/src/commonTest/kotlin/org/hexworks/zircon/internal/component/impl/ComponentStub.kt
  - zircon.core/src/commonTest/kotlin/org/hexworks/zircon/internal/fragment/impl/DefaultMatrixTabBarTest.kt
---

## Decided
2026-11-17, refactor 1 of the component handover. One component, `BinarySelector`, with the role picking the look (`CHECK_BOX`, `RADIO_BUTTON`, `TOGGLE_BUTTON` stay as roles so the themes keep their entries) and `allowUnselect` picking the behavior (`false` = radio: a click never unselects, only the group does). The old per-kind state enums are gone: a renderer derives the glyph from `isPressed` (between press and release) and `isSelected`. Press/release/cancel-outside behavior from the 2026-10-31 learning applies to all three now, so the toggle button commits on release too.
`Selectable` got `key`; the builders derive it from the text (`Selectable.keyFrom`: lower case, runs of non-alphanumerics to one `-`) and fail when there is neither text nor key, as the handover decided. `ExclusiveSelectionGroup<T>` replaces `RadioButtonGroup`, requires non-blank unique keys, drops the selection when the selected member detaches and disposes that member's subscription (the old group leaked it).
Old names stay as deprecated typealiases (`CheckBox`, `RadioButton`, `ToggleButton`, `RadioButtonGroup`, `RadioButtonGroupBuilder`, `CheckBoxAlignment`) and deprecated extensions (`selectedButton`, `radioButtons`, `radioButtonGroup { }`); the builders `CheckBoxBuilder` / `RadioButtonBuilder` / `ToggleButtonBuilder` are thin subclasses of `BinarySelectorBuilder` so the DSL and `ComponentFactory` did not change.

## Tried and ruled out
- One builder class with the kind as a parameter: the reserved space for the mark is a constructor argument of `ComponentWithTextBuilder`, and the no-arg `CheckBoxBuilder()` etc. are public API used by tests and the factory. Subclasses keep both.
- Keeping `CheckBox` etc. as thin sub-interfaces instead of typealiases: the one implementation would have to implement all three and keep the dead state enums.

## Watch out
- A selector without text and without a key does not build (`A selector without text needs a key.`); icon-only check boxes need `key = ...`.
- `where T : Component, T : Selectable` after a `by` delegation does not parse (`by groupDelegate where` reads as an infix call); put the delegated supertype first.
- The deprecated typealiases still produce deprecation warnings at every use; internal code uses `BinarySelector` directly.

In the code:
- `zircon.core/.../api/component/BinarySelector.kt`, `ExclusiveSelectionGroup.kt`, `api/behavior/Selectable.kt` (`key`, `keyFrom`).
- `internal/component/impl/DefaultBinarySelector.kt`, `DefaultExclusiveSelectionGroup.kt`.
- `api/builder/component/BinarySelectorBuilder.kt` (+ the three subclasses), `ExclusiveSelectionGroupBuilder.kt` (`exclusiveSelectionGroup { }`, `selectorGroup { }`), `RadioButtonGroupBuilder.kt` (deprecations).
- Renderers: `DefaultCheckBoxRenderer.markFor`, `DefaultRadioButtonRenderer.markFor`, `NeoCheckBoxRenderer` typed on `BinarySelector`.
- Tests: `DefaultBinarySelectorTest`, `DefaultExclusiveSelectionGroupTest`, `UIEventToComponentDispatcherTest` (`isPressed`).
