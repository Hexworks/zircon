package org.codetome.zircon.component

import org.codetome.zircon.graphics.style.StyleSet

data class ComponentStyles(val defaultStyle: StyleSet,
                           val hoverStyle: StyleSet = defaultStyle,
                           val activeStyle: StyleSet = defaultStyle)