package ru.innopolis;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alexander Chuvashov on 10.11.2016.
 */

class FileOperation implements ITextOperation {
    ThreadMonitor monitor;
    String pattern;
    String replacePattern = "(?U)[\\pP\\s\\d\\�\\<\\>\\№\\-\\+]";


    public FileOperation(ThreadMonitor monitor, String pattern) {
        this.monitor = monitor;
        this.pattern = pattern;
    }

    public FileOperation(ThreadMonitor monitor, String pattern, String replacePattern) {
        this.monitor = monitor;
        this.pattern = pattern;
        this.replacePattern = replacePattern;
    }

    /**
     * Метод проверяет корректность слова через regex выражение и в случае корректности
     * добавляем его в итоговый список и/или увеличиваем количество в списке на единицу
     * @param word - проверяемое слово
     * @return boolean - ture в случае, если слово корректно, false - если слово содержит запрещённые символы
     */
    public boolean checkWord(String word) {
        word = word.toLowerCase();
        word = word.replaceAll(replacePattern, "");
        if (word.length() > 0) {
            if (word.matches(pattern)) {
                synchronized (monitor) {
                    if (monitor.isInterrupted()) {
                        Thread.interrupted();
                    }
                    Long check = Main.m.get(word);
                    Main.m.put(word, check == null ? 1 : check + 1);
                    return true;
                }
            }
            else {
                return false;
            }
        }
        return true;
    }

    /**
     * Метод разбивает строку по пробельному символу на слова (так же учитывается перенос строки или табуляция)
     * @param s - строка, содержащая слова
     * @return LinkedList<String> - содержит список слов полученных из строки s
     */
    public LinkedList<String> textWordSplit(String s) {
        return new LinkedList<String>(Arrays.asList(s.replaceAll("[\n\r\t]"," ").split(" ")));
    }

    /**
     * Метод проверяет строку на наличие латинских символов
     * @param s - строка для проверки
     * @return boolean - ture, если присутствует(ют) латинский символ(ы), false - в ином случае
     */
    public boolean isExistIllegalSymbols(String s, String illegalPattern) {

        Pattern p = Pattern.compile(illegalPattern);
        Matcher m = p.matcher(s);
        return m.find();
    }
}

