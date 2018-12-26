package org.hexworks.zircon.internal.util

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture

object Assets {
    val MANAGER: AssetManager

    init {
        MANAGER = AssetManager()
    }

    fun getCP437TextureDescriptor(tilesetName: String) : AssetDescriptor<Texture> {
        return AssetDescriptor<Texture>(tilesetName.substring(1), Texture::class.java)
    }
}