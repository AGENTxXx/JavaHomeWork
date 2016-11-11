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
    public void testCheckWord() throws Exception {
        assertTrue(textOperation.checkWord("истина") == true);
    }

    @Test
    public void testTextWordSplit() throws Exception {
        String s = "делим строку на отдельные слова";
        List list = new LinkedList<> (Arrays.asList(s.replaceAll("[\n\r\t]"," ").split(" ")));
        assertEquals(textOperation.textWordSplit(s),list);

    }

    @Test
    public void testIsExistIllegalSymbols() throws Exception {
        assertTrue(textOperation.isExistIllegalSymbols("строка содержит bad символы") == true);
        assertTrue(textOperation.isExistIllegalSymbols("эта строка корректна") == false);
    }
}