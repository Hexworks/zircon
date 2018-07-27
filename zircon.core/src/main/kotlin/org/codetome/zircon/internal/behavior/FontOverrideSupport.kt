package org.codetome.zircon.internal.behavior

import org.codetome.zircon.api.data.Size

interface FontOverrideSupport {

    /**
     * Tells what tileset size is supported by the class implementing [FontOverrideSupport].
     */
    fun getSupportedFontSize(): Size
}
