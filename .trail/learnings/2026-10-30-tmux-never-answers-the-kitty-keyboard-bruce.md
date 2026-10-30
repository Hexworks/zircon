---
title: "tmux never answers the kitty keyboard query (CSI ? u), so TerminalSession.handshake() aborts inside tmux even with extended-keys csi-u"
author: Bruce
date: 2026-10-30
branch: master
id: l-298d125d
scope: repo
status: active
src: agent
touches:
  - zircon.terminal/src/commonMain/kotlin/org/hexworks/zircon/terminal/RunTerminal.kt
---

## Decided
Crash 2026-10-30 13:40 of zircon.examples.terminal.kexe (SIGABRT, uncaught IllegalStateException "This terminal does not support the kitty keyboard protocol") was the `check(kittyFlags != null)` in zircon.terminal/src/commonMain/kotlin/org/hexworks/zircon/terminal/RunTerminal.kt handshake(). Process ran inside tmux (TERM_PROGRAM=tmux) in foot 1.28.

## Tried and ruled out
- Not OOM, not a terminal bug, not a package update. Core held the exact reply: only `ESC[?1;2;4c` (DA1), no `ESC[?<flags>u`.
- Probed empirically: tmux 3.6a (homebrew, user's running server) and tmux 3.7c (isolated `-L` server) with `extended-keys on` + `extended-keys-format csi-u` both answer DA1 and silently drop `CSI ? u`. tmux's csi-u support is only the *key output format*; it does not implement the kitty flags query/reply.

## Watch out
- The error message's "(in tmux: 3.4+)" hint is wrong: no tmux version answers the query. Fix needs a tmux-aware path (e.g. when $TMUX is set, skip the query and trust csi-u keys / push `CSI > flags u` anyway), or a documented "run outside tmux" rule. Test `failsFastWithoutTheKittyProtocolAndRestoresTheTerminal` encodes the current behaviour.
