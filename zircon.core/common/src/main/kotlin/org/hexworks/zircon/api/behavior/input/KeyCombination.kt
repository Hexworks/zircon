package org.hexworks.zircon.api.behavior.input

import org.hexworks.zircon.api.input.InputType

data class KeyCombination(val char: Char = ' ',
                          val inputType: InputType = InputType.Character,
                          val shiftState: ShiftState = ShiftState.SHIFT_UP,
                          val ctrlState: CtrlState = CtrlState.CTRL_UP,
                          val altState: AltState = AltState.ALT_UP)
