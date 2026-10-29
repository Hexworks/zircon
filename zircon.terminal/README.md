# zircon.terminal

The terminal renderer: `runTerminal(grid)` draws a `TileGrid` as text in the terminal the
process runs in, on the JVM or as a native Linux/macOS binary. See the
[main README](../README.md#in-the-terminal) for what it supports.

## How it works

- `TerminalRenderer` is core's `BaseRenderer` tick; `TextTileset` writes one cell (code
  point, foreground, background) per tile into a `CellBuffer`.
- `AnsiEncoder` diffs the buffer against the previous frame and writes only changed cells,
  with one SGR per colour change, cursor moves only across gaps, and the whole frame inside
  a synchronized update (`CSI ?2026h` … `CSI ?2026l`). An unchanged frame writes nothing.
- `InputParser` turns kitty keyboard protocol and SGR mouse sequences into Zircon events.
- `Tty` is the device: POSIX termios/`poll` on native, `stty` plus `sun.misc.Signal` on the
  JVM. `TerminalSession` owns the loop and restores the terminal on every exit path.

Tests: `commonTest` runs the parser, the encoder and the session against a scripted `Tty`
on the JVM and on linuxX64; `jvmTest` holds golden escape-sequence files
(`src/jvmTest/resources/golden`, refresh with `UPDATE_GOLDENS=1`).

## Release checklist (by hand)

Run `make demo-terminal` in each of foot, ghostty, Alacritty, kitty and WezTerm, then:

- [ ] The tab bar, header and buttons of the Components demo look like the desktop demos
      (colours, box drawing, no stray characters).
- [ ] Tab (focus), Enter/Space (activate), Esc and arrow keys work; a held key repeats.
- [ ] Mouse: hovering highlights a button, clicking activates it, the wheel scrolls a list,
      dragging reports where it goes.
- [ ] Switching tabs with the mouse rebuilds the grid without garbage left behind.
- [ ] Shrinking the window below the grid shows the "terminal too small" banner; growing it
      back redraws the grid centred.
- [ ] The Benchmark demo (`make demo-terminal DEMO=Benchmark`) in a 1080p 8x8-cell window
      (240x135) reports at least 30 fps on exit, with `render` + `encode` under 4 ms on the
      JVM.
- [ ] Ctrl+C exits, the shell prompt is intact (echo, line editing, cursor visible), and
      the fps line was printed.
- [ ] `kill -TERM <pid>` from another terminal exits just as cleanly.
- [ ] A terminal without the kitty protocol (e.g. plain `xterm`) prints the "does not
      support the kitty keyboard protocol" message and leaves the shell intact.
