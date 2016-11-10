package ru.innopolis;

/**
 * Created by Alexander Chuvashov on 10.11.2016.
 */
public class ExtendFileOperation extends FileOperation implements IExtendTextOperation {
    public ExtendFileOperation(ThreadMonitor monitor, String pattern) {
        super(monitor, pattern);
    }

    public ExtendFileOperation(ThreadMonitor monitor, String pattern, String replacePattern) {
        super(monitor, pattern, replacePattern);
    }

    /**
     * Метод позволяет найти и вернуть позицию последнего пробела в строке
     * @param s - строка, в которой ищется позиция последнего пробела
     * @return int - возвращает позицию последнего пробела или -1, если пробел не найден
     */
    public long lastSpacePosition(String s) {
        return s.lastIndexOf(" ");
    }

    /**
     * Метод проверяет является ли последний символ в строке частично обрезанным и содержит символ
     * ошибочного символа в Unicode
     * @param s - строка, в которой происходит проверка на корректность символа
     * @return boolean - возвращает true, если последний символ коректен, иначе false
     */
    public boolean checkCorrectLastSymbol(String s) {
        if ((int)s.charAt(s.length()-1) == 65533) {
            return false;
        }
        else {
            return true;
        }
    }
}
