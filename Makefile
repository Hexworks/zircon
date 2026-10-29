# ─────────────────────────────────────────────────────────────────────────────
# Makefile for Zircon
# ─────────────────────────────────────────────────────────────────────────────
#
# Thin wrappers around the Gradle build so the common tasks have short names:
#
#   make build      ← compile every module for every target
#   make test       ← run the whole check (tests on every host target, API checks)
#   make demo       ← run the demos app, asks whether to open it on the desktop or in the browser
#   make            ← runs the first target (build) by default
#
# Each target has a name, optional dependencies (other targets that must run
# first), and one or more shell commands indented with a TAB character.
# The TAB indentation is mandatory — spaces will not work.
#
# ─────────────────────────────────────────────────────────────────────────────


# ─── Variables ───────────────────────────────────────────────────────────────
#
#   :=   "Simple assignment" — evaluated once when the Makefile is read.
#   ?=   "Conditional assignment" — only sets the variable if it is NOT already
#        defined, so it can be overridden from the command line:
#          make demo PLATFORM=browser

# The Gradle wrapper; everything goes through it so the pinned Gradle is used.
GRADLE    := ./gradlew

# Extra flags for every Gradle call. --console=plain keeps CI logs readable;
# override with GRADLE_FLAGS="" for the rich console.
GRADLE_FLAGS ?= --console=plain

# Which platform `make demo` opens: "desktop", "browser", or empty to be asked.
PLATFORM  ?=


# ─── .PHONY declaration ─────────────────────────────────────────────────────
#
# These targets are commands, not files — always run them regardless of
# whether a file with that name exists.

.PHONY: build test demo demo-desktop demo-browser demo-terminal clean


# ─── Targets ─────────────────────────────────────────────────────────────────

# build: Compile every module for every target it publishes (no tests).
#
#   `assemble` is Gradle's "build everything but don't test" lifecycle task.
#   It covers zircon.core (jvm, js, wasmJs, native), zircon.compose (jvm, wasmJs)
#   and the example apps.
#
# Usage: make build

build:
	$(GRADLE) $(GRADLE_FLAGS) assemble

# test: Run the full verification.
#
#   `check` runs the unit tests on every host-runnable target (jvm, js, wasmJs,
#   linuxX64 on Linux), the Compose golden-image tests, the binary API check
#   (`apiCheck`) and the internal-leak check (`checkApiLeaks`).
#
# Usage: make test

test:
	$(GRADLE) $(GRADLE_FLAGS) check

# demo: Open the demos app (zircon.examples) on the desktop or in the browser.
#
#   With PLATFORM unset it asks interactively. $$ is how a Makefile writes a
#   literal $ for the shell, hence $$PLATFORM and $$answer below.
#
# Usage:
#   make demo                    ← asks: desktop or browser?
#   make demo PLATFORM=desktop
#   make demo PLATFORM=browser

demo:
	@platform="$(PLATFORM)"; \
	if [ -z "$$platform" ]; then \
		printf 'Run the demos on (d)esktop, in the (b)rowser or in the (t)erminal? [d/b/t] '; \
		read -r answer; \
		case "$$answer" in \
			d|D|desktop) platform=desktop ;; \
			b|B|browser) platform=browser ;; \
			t|T|terminal) platform=terminal ;; \
			*) echo "Unknown choice: $$answer"; exit 1 ;; \
		esac; \
	fi; \
	case "$$platform" in \
		desktop) $(MAKE) --no-print-directory demo-desktop ;; \
		browser) $(MAKE) --no-print-directory demo-browser ;; \
		terminal) $(MAKE) --no-print-directory demo-terminal ;; \
		*) echo "PLATFORM must be desktop, browser or terminal"; exit 1 ;; \
	esac

# demo-desktop: Launch the demos in a desktop window (Compose for Desktop).
demo-desktop:
	$(GRADLE) $(GRADLE_FLAGS) :zircon.examples.desktop:jvmRun

# demo-browser: Serve the demos with webpack's dev server and open the browser.
#
#   The Kotlin/Wasm dev server picks a free port and opens the default browser
#   itself; stop it with Ctrl+C.
demo-browser:
	$(GRADLE) $(GRADLE_FLAGS) :zircon.examples.web:wasmJsBrowserDevelopmentRun

# demo-terminal: Build the native terminal launcher for this machine and run it in
# this terminal (Gradle does not pass its terminal on, so the binary runs directly).
#
#   DEMO picks the demo to start on, e.g. `make demo-terminal DEMO=Benchmark`.
demo-terminal:
	@case "$$(uname -s)-$$(uname -m)" in \
		Linux-x86_64) target=LinuxX64; dir=linuxX64 ;; \
		Linux-aarch64) target=LinuxArm64; dir=linuxArm64 ;; \
		Darwin-arm64) target=MacosArm64; dir=macosArm64 ;; \
		Darwin-x86_64) target=MacosX64; dir=macosX64 ;; \
		*) echo "No native terminal launcher for $$(uname -s)-$$(uname -m)"; exit 1 ;; \
	esac; \
	$(GRADLE) $(GRADLE_FLAGS) :zircon.examples.terminal:linkReleaseExecutable$$target && \
	ZIRCON_EXAMPLES_FILES=zircon.examples/resources/files \
		zircon.examples.terminal/build/bin/$$dir/releaseExecutable/zircon.examples.terminal.kexe $(DEMO)

# clean: Remove all build outputs.
clean:
	$(GRADLE) $(GRADLE_FLAGS) clean
