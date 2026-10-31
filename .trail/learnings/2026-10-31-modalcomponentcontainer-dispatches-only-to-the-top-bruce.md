---
title: "ModalComponentContainer dispatches only to the top container: walking down the stack on Pass closed every nested modal with one click"
author: Bruce
date: 2026-10-31
branch: master
cost: { minutes: 4, tokens: 40158 }
id: l-87aae470
scope: repo
status: active
src: agent
---

## Decided
`ModalComponentContainer.dispatch` (`zircon.core/.../internal/component/impl/ModalComponentContainer.kt`) hands the event to the top of the stack only. The old loop walked down the stack while the result was `Pass`. `UIEventToComponentDispatcher.performComponentEvents` returns `Pass` for every mouse event even when it activated a button, and a modal's close handler re-activates the container below synchronously, so one click on a "Close" button reached the next modal's "Close" at the same spot and closed the whole stack. Keyboard activation returned `Processed`, which is why space closed them one by one. Regression test: `ModalComponentContainerTest`.

## Watch out
Lower containers are deactivated and return `Pass` without looking at the event, so the loop never did anything useful; dropping it changes nothing else.
