package ru.innopolis;

import java.util.LinkedList;

/**
 * Created by Alexander Chuvashov on 10.11.2016.
 */
public interface ITextOperation {
    boolean checkWord(String word);
    LinkedList<String> textWordSplit(String s);
    boolean isExistIllegalSymbols(String s);
}
