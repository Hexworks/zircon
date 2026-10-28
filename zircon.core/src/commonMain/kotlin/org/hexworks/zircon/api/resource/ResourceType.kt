package org.hexworks.zircon.api.resource

public enum class ResourceType {
    /**
     * Represents a resource on the filesystem (e.g.: a file or a directory)
     */
    FILESYSTEM,

    /**
     * Represents a resource within the project (e.g.: resources folder within a jar file)
     */
    PROJECT
}
