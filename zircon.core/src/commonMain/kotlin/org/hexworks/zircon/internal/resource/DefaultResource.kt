package org.hexworks.zircon.internal.resource

import org.hexworks.zircon.api.resource.Resource
import org.hexworks.zircon.api.resource.ResourceType

data class DefaultResource(override val resourceType: ResourceType, override val path: String) : Resource