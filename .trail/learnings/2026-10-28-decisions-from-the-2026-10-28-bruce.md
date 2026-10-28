---
title: "Decisions from the 2026-10-28 grilling session for the Zircon 2.0 release (targets,…"
author: Bruce
date: 2026-10-28
id: l-48f6928a
scope: repo
status: active
touches:
  - zircon.korge
  - zircon.examples.korge
  - TODO.md
  - "zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/application/Application.kt#Application"
  - "zircon.core/src/commonMain/kotlin/org/hexworks/zircon/renderer/virtual/VirtualApplication.kt#VirtualApplication"
---

## Decided
Decided 2026-10-28 (grill session), not recorded anywhere in the repo yet:

- Compose-only renderer; zircon.korge + zircon.examples.korge get deleted. Core drops korge-core.
- Targets: core = all KMP targets; zircon.compose = jvm + android + wasmJs (no js, Compose web is wasm-only).
- Entry point: composable-first (`@Composable` dropped into the user's tree). Core `Application`/`VirtualApplication`/`start()` collapse to TileGrid + AppConfig + a renderer/tick seam.
- Rendering is THE critical path: prototype both common-DrawScope and pixel-buffer paths, benchmark. Bar: 1080p @ 8x8 tiles (32,400 tiles), full redraw, 60fps desktop + wasm, 30fps mid Android.
- All asset features stay (CP437, TTF, graphical zip tilesets, REXPaint .xp, .zap animations). Metadata YAML → JSON via kotlinx-serialization. Inflate/zip/gzip written in-house (port puff/tinf). Assets live in zircon.compose as Compose resources; core has a `loadBytes` seam + ByteArray APIs.
- Cobalt 2.0 must be released first (repo ~/projects/cobalt): add android/wasmJs/iOS/native targets, drop reflect + logging libs, Java 17 bytecode, Maven Central.
- Drop deps: kotlin-reflect (→ jvmTest only), kotlin-logging/slf4j/logback, mockk, korge. Keep cobalt, kotlinx-collections-immutable, coroutines, kotest, add kotlinx-serialization-json.
- API: bare-noun DSL everywhere (`button {}`; delete `buildX`), `explicitApi()` + binary-compatibility-validator, no `internal` types in public signatures, Kotlin-only (no Java interop).
…

## Watch out
follow this when planning any zircon work; update this file if the user changes a decision. See addamsson-prefs.

_From Claude Code's memory of this repo, last updated Oct 28._
