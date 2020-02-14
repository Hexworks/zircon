package org.hexworks.zircon.internal.component

import org.hexworks.zircon.api.component.AttachedComponent

interface InternalAttachedComponent : InternalComponent, AttachedComponent {

    val component: InternalComponent
    val parentContainer: InternalContainer

}