package org.hexworks.zircon.api.resource

import org.hexworks.zircon.api.data.ImageTile
import org.hexworks.zircon.api.util.Identifier
import kotlin.reflect.KClass

class ImageDictionaryTilesetResource(override val path: String,
                                     override val width: Int = 1,
                                     override val height: Int = 1,
                                     override val id: Identifier = Identifier.randomIdentifier(),
                                     override val tileType: KClass<ImageTile> = ImageTile::class) : TilesetResource
