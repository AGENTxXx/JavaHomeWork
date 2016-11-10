package ru.innopolis;

/**
 * Created by Alexander Chuvashov on 10.11.2016.
 */
public interface IExtendTextOperation {
    long lastSpacePosition(String s);
    boolean checkCorrectLastSymbol(String s);
}
