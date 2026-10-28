package org.hexworks.zircon.internal.component

import org.hexworks.zircon.api.component.AttachedComponent

public interface InternalAttachedComponent : InternalComponent, AttachedComponent {

    public val component: InternalComponent
    public val parentContainer: InternalContainer

}