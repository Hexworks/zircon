package org.hexworks.zircon.renderer.korge.tileset

import korlibs.io.file.VfsFile
import korlibs.io.file.std.localCurrentDirVfs
import korlibs.io.file.std.resourcesVfs
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.resource.TilesetSourceType

suspend fun loadVfs(resource: TilesetResource): VfsFile {
    val vfs = when (resource.tilesetSourceType) {
        TilesetSourceType.FILESYSTEM -> localCurrentDirVfs
        TilesetSourceType.JAR -> resourcesVfs
    }
    val vfsFile = vfs[resource.path]

    if (!vfsFile.exists()) {
        error("File $vfsFile doesn't exist!")
    }
    return vfsFile;
}