---
title: "Every component built was pinned forever: cobalt kept property listeners on a global event bus, and the examples chained each rebuilt screen's error handler in…"
author: Bruce
date: 2026-11-17
branch: master
cost: { minutes: 18, tokens: 119319 }
id: l-e0a24cfa
scope: repo
status: active
src: agent
touches:
  - docs/component-refactoring-handover.md
  - docs/component-themes.md
  - zircon.core/api/zircon.core.api
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/behavior/Selectable.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/builder/component/BinarySelectorBuilder.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/builder/component/CheckBoxBuilder.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/builder/component/ExclusiveSelectionGroupBuilder.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/builder/component/RadioButtonBuilder.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/builder/component/RadioButtonGroupBuilder.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/builder/component/ToggleButtonBuilder.kt
---

## Decided
2026-11-17, after the examples JVM tests ran out of a 512m heap once a third full walk was added. A JFR `OldObjectSample` recording with `path-to-gc-roots` (started from the test itself, printed with `jfr print`) gave the two chains; grepping for globals had found nothing.
- cobalt: `BaseProperty.onChange` and every binding subscribed through the static `Cobalt.eventbus` keyed by the property's `PropertyScope`; the subscription callback captures the listener, so a dropped component (eight property listeners each) stayed reachable from the class loader for ever. Fixed in cobalt (uncommitted in `~/projects/cobalt`, published to `~/.m2` as 2.0.0): `PropertyScope` now owns its subscriptions (copy-on-write list in an `Atom`), with `subscribe` / `publish` / `cancelAll` replacing the bus calls in `BaseProperty` and the seven bindings. Semantics kept: a listener that throws is logged and dropped. Test: `PropertyListenerRetentionTest` (jvmTest, weak references).
- examples: `examplesScreen` installed an `ErrorHandler` closure into `ZirconDefaults.current` that wrapped the previous handler, i.e. the previous screen's closure, so every rebuilt screen (tile size change, tests) stayed alive with its skull animation frames. Now one `ExamplesErrorHandler` object is installed once and forwards to the current screen's handler.
- The examples test heap bump to 1g is gone; the suite passes at the default 512m.
- The two `ExamplesScreenTest` failures were not machine-specific: the tests expected `Zilk 16×16` while the titles use an ASCII `x` since the user's 2026-11-10 commit.
- `DefaultTextAreaTest` is no longer `@Ignore`d: the cursor assertions use `editorState.cursor`, and the five generic state transitions a text area does not follow (it swallows activation and keeps the focus look on release) are overridden with its real behavior.

## Tried and ruled out
- Finding the holder by grepping for `object`s, global scopes and coroutines: both holders were closures stored in places that looked like values (`Cobalt.eventbus`, `ZirconDefaults.current.onError`). The JFR recording took one run.

## Watch out
- Anything put into `ZirconDefaults.current` lives for the process; never store a closure there that captures a screen unless it is replaced, not chained.
- cobalt must be published to `~/.m2` (`./gradlew publishToMavenLocal` in `~/projects/cobalt`) for zircon to pick up the fix; run zircon with `--refresh-dependencies` after.

In the code:
- cobalt: `cobalt.core/.../databinding/internal/event/PropertyScope.kt`, `property/base/BaseProperty.kt`, `binding/*.kt`, `src/jvmTest/.../PropertyListenerRetentionTest.kt`.
- zircon: `zircon.examples/.../ExamplesScreen.kt` (`ExamplesErrorHandler`), `zircon.examples/build.gradle.kts`, `ExamplesScreenTest`, `DefaultTextAreaTest`, `TODO.md` status line.
- Probe recipe: `Recording().enable("jdk.OldObjectSample").with("cutoff","infinity").with("path-to-gc-roots","true")`, allocate, `System.gc()`, dump, `jfr print --events jdk.OldObjectSample file.jfr`.
