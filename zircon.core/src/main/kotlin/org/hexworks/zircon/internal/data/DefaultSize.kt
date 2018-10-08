package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.data.base.BaseSize

data class DefaultSize(override val width: Int,
                       override val height: Int) : BaseSize()
