package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.data.CharacterTile

/**
 * Responsible for iterating over a `[<]` and returning a list
 * of [CharacterTile]s that represents a word
 */
class CharacterTileIterator(private val tileIterator: Iterator<CharacterTile>) : Iterator<List<CharacterTile>> {
    private var tileStore: CharacterTile? = null


    override fun hasNext(): Boolean {
        return tileIterator.hasNext() || tileStore != null
    }

    override fun next(): List<CharacterTile> {
        val textCharacters = mutableListOf<CharacterTile>()

        if (tileStore != null) {
            val result = listOf(tileStore!!)
            tileStore = null
            return result
        }

        while (tileIterator.hasNext()) {
            val charTile = tileIterator.next()

            //this means we have hit the end of a word. Therefore we should store that ending so that we do not
            //lose it and returnThis the word we just got
            if (WORD_ENDINGS.contains(charTile.character)) {
                //if we have not been storing any word we don't need to do anything
                if (textCharacters.isEmpty()) {
                    return listOf(charTile)
                }

                tileStore = charTile
                return textCharacters
            }

            //we're in the middle of a word so lets add it to text characters
            textCharacters.add(charTile)
        }

        return textCharacters
    }

    companion object {
        //these are the characters that when present mean we have hit the end of the word
        private val WORD_ENDINGS = listOf(' ', '\n', '\t')
    }
}
