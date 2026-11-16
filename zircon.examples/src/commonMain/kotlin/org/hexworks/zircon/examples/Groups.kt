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
val EXAMPLE_GROUPS: List<ExampleGroup> = listOf(
    ExampleGroup("animations", animationExamples),
    ExampleGroup("components", componentExamples),
    ExampleGroup("dialogs", dialogExamples),
    ExampleGroup("fragments", fragmentExamples),
    ExampleGroup("game", gameExamples),
    ExampleGroup("modifiers", modifierExamples),
    ExampleGroup("other", otherExamples),
    ExampleGroup("input+layers", inputLayerExamples),
)
