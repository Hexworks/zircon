package org.hexworks.zircon.api.util

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import kotlin.jvm.JvmStatic
import kotlin.random.Random

class BSPTree(var parent: BSPTree?, rec: Rect) {

    companion object {
        @JvmStatic
        var minSize = 12

        fun toMatrix(array: Array<CharArray>, BSPTrees: MutableList<BSPTree?>) {
            var nbr = 48

            for(BSPTree: BSPTree? in BSPTrees) {
                if(BSPTree != null) {

                    var char = nbr.toChar()
                    var rec = BSPTree.room.get()
                    println(char + " " +BSPTree.boundingBox)
                    if(rec != null) {

                        for (y in rec.position.y..rec.position.y + rec.height) {
                            for (x in rec.position.x..rec.position.x + rec.width) {

                                array[y][x] = char
                            }
                        }
                    }
                }
                nbr++
            }
        }

        fun collectRooms(BSPTree: BSPTree?, list: MutableList<BSPTree?>) {
            if(BSPTree?.leftBSPTree == null) {
                list.add(BSPTree)
            } else {
                collectRooms(BSPTree.leftBSPTree, list)
                collectRooms(BSPTree.rightBSPTree, list)
            }
        }
    }

    var boundingBox = rec
    var room = Maybe.empty<Rect>()
    var leftBSPTree: BSPTree? = null
    var rightBSPTree: BSPTree? = null

    init {
        if(boundingBox.width > minSize*2 && boundingBox.height > minSize*2) {
            var dir = Random.nextBoolean();
            if(parent == null) {
                dir = true
            } else if(parent?.parent == null) {
                dir = false
            }

            var lenght = boundingBox.width

            if (dir == true) {
                lenght = boundingBox.height
            }

            //val fringe = (width * 0.3).toInt();
            val splitPos = Random.nextInt(minSize, lenght - minSize)

            if (dir == true) {
                var boundingBoxes = boundingBox.splitVertical(splitPos);
                leftBSPTree = BSPTree(this, boundingBoxes.first)
                rightBSPTree = BSPTree(this, boundingBoxes.second)
            } else {
                var boundingBoxes = rec.splitHorizontal(splitPos);
                leftBSPTree = BSPTree(this, boundingBoxes.first)
                rightBSPTree = BSPTree(this, boundingBoxes.second)
            }
        }
    }

    fun createRooms(BSPTree: BSPTree?) {
        var leaf_ = BSPTree;
        if(leaf_ == null) {
            leaf_ = this
        }

        if(leaf_.leftBSPTree == null) {
            val bb = leaf_.boundingBox
            leaf_.room = Maybe.of(Rect.create(Position.create(bb.position.x + 1, bb.position.y + 1), Size.create(bb.width - 1, bb.height - 1)))
        } else {
            createRooms(leaf_.rightBSPTree)
            createRooms(leaf_.leftBSPTree)
        }
    }



    override fun toString(): String {
        return boundingBox.toString()
    }
}