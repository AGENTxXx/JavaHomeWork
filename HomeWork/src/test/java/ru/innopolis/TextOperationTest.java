package ru.innopolis;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Alexander Chuvashov on 12.11.2016.
 */
public class TextOperationTest {

    TextOperation textOperation = new TextOperation();

    @Test
    public void testCheckWord() {
        assertTrue(textOperation.checkWord("истина") == true);
    }

    @Test
    public void testTextWordSplit() {
        String s = "делим строку на отдельные слова";
        List list = new LinkedList<> (Arrays.asList(s.replaceAll("[\n\r\t]"," ").split(" ")));
        assertEquals(textOperation.textWordSplit(s),list);

    }

    @Test
    public void testIsExistIllegalSymbolsTrue() {
        assertTrue(textOperation.isExistIllegalSymbols("строка содержит bad символы") == true);
    }

    @Test
    public void testIsExistIllegalSymbolsFalse() {
        assertTrue(textOperation.isExistIllegalSymbols("эта строка корректна") == false);
    }
}