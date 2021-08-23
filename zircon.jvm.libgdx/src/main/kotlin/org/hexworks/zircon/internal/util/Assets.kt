package org.hexworks.zircon.internal.util

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader


object Assets {
    val MANAGER: AssetManager = AssetManager()

    init {
        val resolver = InternalFileHandleResolver()
        MANAGER.setLoader(FreeTypeFontGenerator::class.java, FreeTypeFontGeneratorLoader(resolver))
        MANAGER.setLoader(BitmapFont::class.java, ".ttf", FreetypeFontLoader(resolver))
    }

    fun getCP437TextureDescriptor(tilesetName: String): AssetDescriptor<Texture> {
        return AssetDescriptor(tilesetName.substring(1), Texture::class.java)
    }
}
