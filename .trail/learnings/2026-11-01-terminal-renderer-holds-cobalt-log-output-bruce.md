---
title: Terminal renderer holds cobalt log output back while the alt screen is active and prints it to stderr on exit; apps with their own LogSink are left alone
author: Bruce
date: 2026-11-01
branch: master
cost: { minutes: 1, tokens: 2654 }
id: l-51d3c422
scope: repo
status: active
src: agent
touches:
  - ROADMAP.md
---

## Decided
Grill 2026-11-01. Log lines were scribbling over the alt screen in the terminal examples because cobalt's default `ConsoleLogSink` `println`s to stdout, which is the tty. Fix lives in the library, not the app:
- `TerminalSession.run()` (zircon.terminal/.../RunTerminal.kt) swaps `LoggerFactory.sink` for an internal `BufferedLogSink` (capped at 1000 entries, drops oldest, reports the drop count) right after `tty.open()`, and in `finally`, after `leaveModes()` + `tty.close()`, restores the default sink and prints the buffer via the new `Tty.writeError(text)`.
- An app that installed its own `LoggerFactory.sink` keeps it: the session compares against cobalt's `LoggerFactory.defaultSink`, a public val added to cobalt (`~/projects/cobalt`, `LoggerFactory.kt`) for this purpose, since `ConsoleLogSink` is internal there. Such an app is responsible for keeping its sink off the terminal.
- `Tty.writeError` goes to `STDERR_FILENO` in `PosixTty`, `System.err` in `SttyTty`, and `FakeTty.errorText` in tests, so `TerminalSessionTest` can assert the log shows up only after the session.
- `zircon.examples.terminal/main.kt` no longer prints `runtimeErrors` itself (the flushed log already carries them, with stack traces); `ExamplesScreen.runtimeErrors` stays for the tests' empty-list assertion.
- Removed a stray debug `println` in `TextEditor.undo` that bypassed the sink and hit the alt screen on every undo.
- Live logging (stderr when redirected to a file, `isatty` check) is a ROADMAP.md entry, not 2.0.

## Tried and ruled out
- Logging to stderr during the session: stderr is the same tty as stdout, lands on the alt screen too. Writing to the normal screen while the alt screen is active would need switching back and forth and flickers.
- Always writing a log file: hidden surprise writes plus a path to configure.
- Letting the app print the buffered lines (returning them from `TerminalSession`): pushes the same burden onto every app that `main.kt`'s `runtimeErrors` loop had.
- Keeping both the flushed log and the examples' own `runtimeErrors` print: every contained error would print twice.

## Watch out
- Only `reportError` (ERROR), one `warn` in `ModalComponentContainer` and one `error` in compose's `MouseEventListener` log at INFO or above in library code, so a clean run prints nothing on exit. Adding INFO logs to hot paths would dump them all at exit.
- `FakeTty` must implement `writeError`; any new `Tty` implementation too.
- Cobalt is consumed from mavenLocal at 2.0.0 with no version bump for the `defaultSink` addition: after pulling cobalt, run `./gradlew :cobalt.core:publishToMavenLocal` there or zircon.terminal fails to compile.
