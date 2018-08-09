package org.hexworks.zircon.api

import org.hexworks.zircon.api.resource.REXPaintResource
import java.io.File
import java.io.InputStream

object REXPaintResources {

    /**
     * Loads a REXPaint file from the given path.
     */
    @JvmStatic
    fun loadREXFile(path: String): REXPaintResource {
        return REXPaintResource.loadREXFile(File(path).inputStream())
    }

    /**
     * Loads a REXPaint file from the given [InputStream].
     */
    @JvmStatic
    fun loadREXFile(stream: InputStream): REXPaintResource {
        return REXPaintResource.loadREXFile(stream)
    }
}
