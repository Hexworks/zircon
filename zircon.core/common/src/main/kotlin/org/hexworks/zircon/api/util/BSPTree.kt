package org.hexworks.zircon.api.util

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.datatypes.extensions.fold
import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import kotlin.jvm.JvmStatic
import kotlin.random.Random

class BSPTree(rec: Rect, var parent: Maybe<BSPTree> = Maybe.empty()) {

    var boundingBox = rec
    var leftBSPTree = Maybe.empty<BSPTree>()
    var rightBSPTree = Maybe.empty<BSPTree>()

    private var room = Maybe.empty<Rect>()

    init {
        if (boundingBox.width > minSize * 2 && boundingBox.height > minSize * 2) {
            var dir = Random.nextBoolean()
            if (parent.isEmpty()) {
                dir = true
            } else if (parent.isEmpty() || parent.get().parent.isEmpty()) {
                dir = false
            }
            var lenght = boundingBox.width
            if (dir) {
                lenght = boundingBox.height
            }
            val splitPos = Random.nextInt(minSize, lenght - minSize)
            if (dir) {
                val boundingBoxes = boundingBox.splitVertical(splitPos)
                leftBSPTree = Maybe.of(BSPTree(boundingBoxes[0], Maybe.of(this)))
                rightBSPTree = Maybe.of(BSPTree(boundingBoxes[1], Maybe.of(this)))
            } else {
                val boundingBoxes = rec.splitHorizontal(splitPos)
                leftBSPTree = Maybe.of(BSPTree(boundingBoxes[0], Maybe.of(this)))
                rightBSPTree = Maybe.of(BSPTree(boundingBoxes[1], Maybe.of(this)))
            }
        }
    }

    fun createRooms(BSPTree: BSPTree = this) {
        BSPTree.leftBSPTree.fold(whenEmpty = {
            val bb = BSPTree.boundingBox
            BSPTree.setRoom(Rect.create(Position.create(bb.position.x + 1, bb.position.y + 1), Size.create(bb.width - 1, bb.height - 1)))
        }, whenPresent = {
            BSPTree.rightBSPTree.map {
                createRooms(it)
            }
            BSPTree.leftBSPTree.map {
                createRooms(it)
            }
        })
    }

    override fun toString(): String {
        return boundingBox.toString()
    }

    fun whenHasRoom(fn: (Rect) -> Unit) {
        room.map(fn)
    }

    fun setRoom(room: Rect) {
        this.room = Maybe.of(room)
    }

    companion object {
        @JvmStatic
        var minSize = 12

        fun toMatrix(array: Array<CharArray>, BSPTrees: MutableList<BSPTree>) {
            var nbr = 48
            for (BSPTree: BSPTree in BSPTrees) {
                val char = nbr.toChar()
                BSPTree.whenHasRoom { rec ->
                    for (y in rec.position.y..rec.position.y + rec.height) {
                        for (x in rec.position.x..rec.position.x + rec.width) {
                            array[y][x] = char
                        }
                    }
                }
                nbr++
            }
        }

        fun collectRooms(BSPTree: BSPTree, list: MutableList<BSPTree> = mutableListOf()) {
            if (BSPTree.leftBSPTree.isPresent) {
                list.add(BSPTree)
            } else {
                BSPTree.leftBSPTree.map {
                    collectRooms(it, list)
                }
                BSPTree.rightBSPTree.map {
                    collectRooms(it, list)
                }
            }
        }
    }
}
