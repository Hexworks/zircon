package org.hexworks.zircon.api.resource

import korlibs.io.dynamic.dyn
import korlibs.io.file.VfsFile
import korlibs.io.file.std.localCurrentDirVfs
import korlibs.io.file.std.resourcesVfs
import korlibs.io.serialization.yaml.Yaml

suspend fun loadResource(resource: Resource): VfsFile {
    val vfs = when (resource.resourceType) {
        ResourceType.FILESYSTEM -> localCurrentDirVfs
        ResourceType.PROJECT -> resourcesVfs
    }
    val vfsFile = vfs[resource.path]

    if (!vfsFile.exists()) {
        error("${resource.resourceType} resource '$vfsFile' doesn't exist!")
    }
    return vfsFile;
}

typealias ZipFile = VfsFile

suspend fun ZipFile.extractFile(name: String) = this[name]

fun String.asYaml() = Yaml.decode(this).dyn