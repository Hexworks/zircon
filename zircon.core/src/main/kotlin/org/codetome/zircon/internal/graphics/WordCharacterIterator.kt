package org.codetome.zircon.internal.graphics

import org.codetome.zircon.api.data.Tile

/**
 * Responsible for iterating over a [<] and returning a list
 * of TextCharacters that represents a word
 */
class WordCharacterIterator(private val tileIterator: Iterator<Tile>) : Iterator<List<Tile>> {
    private var characterStore: Tile? = null


    override fun hasNext(): Boolean {
        return tileIterator.hasNext() || characterStore != null
    }

    override fun next(): List<Tile> {
        val textCharacters = ArrayList<Tile>()

        if (characterStore != null) {
            val result = listOf(characterStore!!)
            characterStore = null
            return result
        }

        while (tileIterator.hasNext()) {
            val textCharacter = tileIterator.next()

            //this means we have hit the end of a word. Therefore we should store that ending so that we do not
            //lose it and return the word we just got
            if (WORD_ENDINGS.contains(textCharacter.getCharacter())) {
                //if we have not been storing any word we don't need to do anything
                if (textCharacters.isEmpty()) {
                    return listOf(textCharacter)
                }

                characterStore = textCharacter
                return textCharacters
            }

            //we're in the middle of a word so lets add it to text characters
            textCharacters.add(textCharacter)
        }

        return textCharacters
    }

    companion object {
        //these are the characters that when present mean we have hit the end of the word
        private val WORD_ENDINGS = listOf(' ', '\n', '\t')
    }
}
