package org.hexworks.zircon.examples.utilities

import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.util.BSPTree

fun main(args : Array<String>) {
    getMap(120, 120)
}

fun getMap(width: Int, height: Int) {
    println("Start generating the BSP map")

    val r = Rect.create(Positions.create(0, 0), Sizes.create(width, height));

    val root = BSPTree(null, r)
    root.createRooms(null)

    var list = mutableListOf<BSPTree?>()
    BSPTree.collectRooms(root, list)

    val matrix = Array(height+1, {CharArray(width+1, {' '})})

    BSPTree.toMatrix(matrix, list)

    for(row in matrix) {
        for(j in row) {
            print(j)
        }
        println("")
    }
}






