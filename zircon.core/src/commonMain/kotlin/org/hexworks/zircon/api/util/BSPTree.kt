package org.hexworks.zircon.api.util


import org.hexworks.cobalt.databinding.api.extension.fold
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import kotlin.jvm.JvmStatic
import kotlin.random.Random

class BSPTree(rec: Rect, var parent: BSPTree? = null) {

    var boundingBox = rec
    var leftBSPTree: BSPTree? = null
    var rightBSPTree: BSPTree? = null

    private var room: Rect? = null

    init {
        if (boundingBox.width > minSize * 2 && boundingBox.height > minSize * 2) {
            var dir = Random.nextBoolean()
            if (parent == null) {
                dir = true
            } else if (parent == null || parent!!.parent == null) {
                dir = false
            }
            var lenght = boundingBox.width
            if (dir) {
                lenght = boundingBox.height
            }
            val splitPos = Random.nextInt(minSize, lenght - minSize)
            if (dir) {
                val boundingBoxes = boundingBox.splitVertical(splitPos)
                leftBSPTree = BSPTree(boundingBoxes.first, this)
                rightBSPTree = BSPTree(boundingBoxes.second, this)
            } else {
                val boundingBoxes = rec.splitHorizontal(splitPos)
                leftBSPTree = BSPTree(boundingBoxes.first, this)
                rightBSPTree = BSPTree(boundingBoxes.second, this)
            }
        }
    }

    fun createRooms(BSPTree: BSPTree = this) {
        BSPTree.leftBSPTree.fold(whenNull = {
            val bb = BSPTree.boundingBox
            BSPTree.setRoom(
                Rect.create(
                    Position.create(bb.position.x + 1, bb.position.y + 1),
                    Size.create(bb.width - 1, bb.height - 1)
                )
            )
        }, whenNotNull = {
            BSPTree.rightBSPTree?.let {
                createRooms(it)
            }
            BSPTree.leftBSPTree?.let {
                createRooms(it)
            }
        })
    }

    override fun toString(): String {
        return boundingBox.toString()
    }

    fun whenHasRoom(fn: (Rect) -> Unit) {
        room?.let(fn)
    }

    fun setRoom(room: Rect) {
        this.room = room
    }

    companion object {
        @JvmStatic

        var minSize = 6

        fun toMatrix(array: Array<CharArray>, BSPTrees: MutableList<BSPTree>) {
            var nbr = 48
            for (BSPTree: BSPTree in BSPTrees) {
                val char = nbr.toChar()
                BSPTree.whenHasRoom { rec ->

                    for (y in rec.position.y..rec.position.y + rec.height - 1) {
                        for (x in rec.position.x..rec.position.x + rec.width - 1) {
                            array[y][x] = char
                        }
                    }
                }
                nbr++
            }
        }

        fun collectRooms(BSPTree: BSPTree, list: MutableList<BSPTree> = mutableListOf()) {
            if (BSPTree.leftBSPTree != null) {
                list.add(BSPTree)
            } else {
                BSPTree.leftBSPTree?.let {
                    collectRooms(it, list)
                }
                BSPTree.rightBSPTree?.let {
                    collectRooms(it, list)
                }
            }
        }
    }
}
