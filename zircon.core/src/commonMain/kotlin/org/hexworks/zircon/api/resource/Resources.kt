package org.hexworks.zircon.api.resource

import korlibs.io.file.VfsFile
import korlibs.io.file.std.localCurrentDirVfs
import korlibs.io.file.std.resourcesVfs

suspend fun Resource.load(): VfsFile {
    val resource = this
    val vfs = when (resource.resourceType) {
        ResourceType.FILESYSTEM -> localCurrentDirVfs
        ResourceType.PROJECT -> resourcesVfs
    }
    val vfsFile = vfs[resource.path]

    if (!vfsFile.exists()) {
        error("${resource.resourceType} resource '$vfsFile' doesn't exist!")
    }
    return vfsFile
}

suspend fun Resource.loadResourceFile(): VfsFile = load().apply {
    check(isFile()) { "Resource '${this.path}' is not a file!" }
}


fun VfsFile.loadFile(name: String): VfsFile = this[name]
