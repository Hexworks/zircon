package org.hexworks.zircon.examples

import org.hexworks.zircon.examples.animations.animationExamples
import org.hexworks.zircon.examples.components.componentExamples
import org.hexworks.zircon.examples.dialogs.dialogExamples
import org.hexworks.zircon.examples.fragments.fragmentExamples
import org.hexworks.zircon.examples.game.gameExamples
import org.hexworks.zircon.examples.inputlayers.inputLayerExamples
import org.hexworks.zircon.examples.modifiers.modifierExamples
import org.hexworks.zircon.examples.other.otherExamples

/** Every package in tab order. */
public val examplePackages: List<ExamplePackage> = listOf(
    ExamplePackage("animations", animationExamples),
    ExamplePackage("components", componentExamples),
    ExamplePackage("dialogs", dialogExamples),
    ExamplePackage("fragments", fragmentExamples),
    ExamplePackage("game", gameExamples),
    ExamplePackage("modifiers", modifierExamples),
    ExamplePackage("other", otherExamples),
    ExamplePackage("input+layers", inputLayerExamples),
)
