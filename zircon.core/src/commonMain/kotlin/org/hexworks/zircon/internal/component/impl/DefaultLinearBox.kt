package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.behavior.TitleOverride
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.data.Orientation
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.InternalAttachedComponent
import org.hexworks.zircon.internal.component.InternalComponent

/**
 * A [DefaultContainer] that lays its children out one after the other along [axis]:
 * [DefaultHBox] from left to right, [DefaultVBox] from top to bottom. Children keep their
 * size; a child's position along the axis is the sum of the extents (and [spacing]s) of the
 * children before it. Detaching a child closes the gap it leaves.
 */
public abstract class DefaultLinearBox internal constructor(
    componentMetadata: ComponentMetadata,
    initialTitle: String,
    private val spacing: Int,
    private val axis: Orientation,
    renderingStrategy: ComponentRenderingStrategy<out Component>
) : DefaultContainer(
    metadata = componentMetadata,
    renderer = renderingStrategy
),
    TitleOverride by TitleOverride.create(initialTitle) {

    /** The extent along the axis the children (and their spacings) take up. */
    private var filled = 0

    /** The extent along the axis left for new children. */
    public val remainingSpace: Int
        get() = extentOf(contentSize) - filled

    override fun addComponent(component: Component): InternalAttachedComponent {
        require(component is InternalComponent) {
            "The supplied component does not implement required interface: InternalComponent."
        }
        val gap = if (children.isEmpty()) 0 else spacing
        require(fits(component, gap)) {
            "There is not enough space ${availableSpace()} left in $this to add $component as a child."
        }
        component.moveTo(component.position + step(filled + gap))
        filled += gap + extentOf(component.size)
        return LinearAttachment(super.addComponent(component))
    }

    /**
     * [DefaultContainer.detachAllComponents] detaches the undecorated attachments, so the
     * layout bookkeeping has to be reset here or nothing fits after a detach all.
     */
    override fun detachAllComponents(): List<Component> {
        val detached = super.detachAllComponents()
        filled = 0
        return detached
    }

    // The decorator delegates to the attached component, so names like `children` would
    // resolve to that component's members in here: the box does the work in [closeGapOf].
    private inner class LinearAttachment(
        val attachment: InternalAttachedComponent
    ) : InternalAttachedComponent by attachment {
        override fun detach(): Component {
            closeGapOf(attachment.component)
            return attachment.detach()
        }
    }

    /** Closes the gap [component] leaves when it is detached: the children after it move up. */
    private fun closeGapOf(component: Component) {
        val delta = extentOf(component.size) + if (children.size == 1) 0 else spacing
        val start = along(component.position)
        children.filter { along(it.position) > start }.forEach { it.shiftBy(step(-delta)) }
        filled -= delta
    }

    private fun fits(component: Component, gap: Int): Boolean =
        extentOf(component.size) + gap <= remainingSpace && acrossOf(component.size) <= acrossOf(contentSize)

    private fun availableSpace(): Size =
        if (axis == Orientation.HORIZONTAL) Size(remainingSpace, contentSize.height) else Size(contentSize.width, remainingSpace)

    private fun extentOf(size: Size): Int = if (axis == Orientation.HORIZONTAL) size.width else size.height
    private fun acrossOf(size: Size): Int = if (axis == Orientation.HORIZONTAL) size.height else size.width
    private fun along(position: Position): Int = if (axis == Orientation.HORIZONTAL) position.x else position.y
    private fun step(extent: Int): Position = if (axis == Orientation.HORIZONTAL) Position(extent, 0) else Position(0, extent)
}
