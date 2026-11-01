---
title: "Compose Hot Reload gutter option needs the org.jetbrains.compose.hot-reload Gradle plugin on the module; IDEA reuses a stale 'zircon [jvm]' run config by name"
author: Bruce
date: 2026-11-01
branch: master
cost: { minutes: 6, tokens: 91413 }
id: l-bc68a473
scope: repo
status: active
src: agent
touches:
  - ROADMAP.md
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/internal/component/impl/texteditor/TextEditor.kt
  - zircon.examples.terminal/src/commonMain/kotlin/main.kt
  - zircon.terminal/src/commonMain/kotlin/org/hexworks/zircon/terminal/BufferedLogSink.kt
  - zircon.terminal/src/commonMain/kotlin/org/hexworks/zircon/terminal/RunTerminal.kt
  - zircon.terminal/src/commonMain/kotlin/org/hexworks/zircon/terminal/Tty.kt
  - zircon.terminal/src/commonTest/kotlin/org/hexworks/zircon/terminal/FakeTty.kt
  - zircon.terminal/src/commonTest/kotlin/org/hexworks/zircon/terminal/TerminalSessionTest.kt
  - zircon.terminal/src/jvmMain/kotlin/org/hexworks/zircon/terminal/SttyTty.kt
  - zircon.terminal/src/nativeMain/kotlin/org/hexworks/zircon/terminal/PosixTty.kt
---

## Decided
2026-11-01. `zircon.examples.desktop` applies `org.jetbrains.compose.hot-reload` (`compose-hot-reload` alias in `gradle/libs.versions.toml`, foojay resolver in `settings.gradle.kts` so the plugin fetches the JetBrains Runtime). The IDE's "Run … with Compose Hot Reload" gutter action only appears for modules with that Gradle plugin; having the Compose/KMP IDE plugin installed is not enough. CLI: `./gradlew :zircon.examples.desktop:hotRunJvm`.

## Tried and ruled out
Nothing else tried; the plugin is the documented gate.

## Watch out
- IDEA names a gutter-created KMP run config `<rootProject> [jvm]`, i.e. `zircon [jvm]`, and reuses an existing one of that name. A stale one pointing at `zircon.examples.compose` (no `main`) gives "Could not find or load main class MainKt" when running `main.kt` in `zircon.examples.desktop`. Delete it in Run > Edit Configurations before clicking the gutter again; rename the new one so later gutter runs in other modules don't collide.
- `hotMcpServerJvm` exists: an MCP server for driving the running app from an agent.
