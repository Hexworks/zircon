package org.codetome.zircon.internal.behavior

import org.codetome.zircon.api.Size

interface FontOverrideSupport {

    /**
     * Tells what font size is supported by the class implementing [FontOverrideSupport].
     */
    fun getSupportedFontSize(): Size
}