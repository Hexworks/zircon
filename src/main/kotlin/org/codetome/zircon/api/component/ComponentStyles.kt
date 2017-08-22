package org.codetome.zircon.api.component

import org.codetome.zircon.api.graphics.StyleSet

data class ComponentStyles(val defaultStyle: StyleSet,
                           val hoverStyle: StyleSet = defaultStyle,
                           val activeStyle: StyleSet = defaultStyle)