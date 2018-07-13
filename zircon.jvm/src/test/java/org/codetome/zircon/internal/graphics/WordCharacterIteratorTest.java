package org.codetome.zircon.internal.graphics;

import org.codetome.zircon.api.TextCharacter;
import org.codetome.zircon.api.interop.TextCharacters;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class WordCharacterIteratorTest {


  @Test
  public void whenIteratorHasNoWords() throws Exception {
    Iterator<TextCharacter> iterator = generateTextCharacters(null).iterator();
    WordCharacterIterator wordCharacterIterator = new WordCharacterIterator(iterator);
    assertFalse(wordCharacterIterator.hasNext());
  }

  @Test
  public void whenIteratorHasOneWords() throws Exception {
    Iterator<TextCharacter> iterator = generateTextCharacters("ThisIsATestToSeeWhatThisLooksLike").iterator();
    WordCharacterIterator wordCharacterIterator = new WordCharacterIterator(iterator);
    assertTrue(wordCharacterIterator.hasNext());
    assertEquals(generateTextCharacters("ThisIsATestToSeeWhatThisLooksLike"), wordCharacterIterator.next());
    assertFalse(wordCharacterIterator.hasNext());
  }

  @Test
  public void whenIteratorHasMultipleWords() throws Exception {
    Iterator<TextCharacter> iterator = generateTextCharacters("This is a test too.").iterator();
    WordCharacterIterator wordCharacterIterator = new WordCharacterIterator(iterator);

    assertTrue(wordCharacterIterator.hasNext());
    assertEquals(generateTextCharacters("This"), wordCharacterIterator.next());

    assertTrue(wordCharacterIterator.hasNext());
    assertEquals(generateTextCharacters(" "), wordCharacterIterator.next());

    assertTrue(wordCharacterIterator.hasNext());
    assertEquals(generateTextCharacters("is"), wordCharacterIterator.next());

    assertTrue(wordCharacterIterator.hasNext());
    assertEquals(generateTextCharacters(" "), wordCharacterIterator.next());

    assertTrue(wordCharacterIterator.hasNext());
    assertEquals(generateTextCharacters("a"), wordCharacterIterator.next());

    assertTrue(wordCharacterIterator.hasNext());
    assertEquals(generateTextCharacters(" "), wordCharacterIterator.next());

    assertTrue(wordCharacterIterator.hasNext());
    assertEquals(generateTextCharacters("test"), wordCharacterIterator.next());

    assertTrue(wordCharacterIterator.hasNext());
    assertEquals(generateTextCharacters(" "), wordCharacterIterator.next());

    assertTrue(wordCharacterIterator.hasNext());
    assertEquals(generateTextCharacters("too."), wordCharacterIterator.next());

    assertFalse(wordCharacterIterator.hasNext());
  }

  private List<TextCharacter> generateTextCharacters(String data) {
    List<TextCharacter> textCharacters = new ArrayList<>();

    //forces an empty iterator
    if(data == null){
      return textCharacters;
    }

    for (char character : data.toCharArray()) {
      TextCharacter textCharacter = TextCharacters.newBuilder().character(character).build();
      textCharacters.add(textCharacter);
    }

    return textCharacters;
  }
}
