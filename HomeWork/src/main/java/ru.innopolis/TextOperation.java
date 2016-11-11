package ru.innopolis;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static ru.innopolis.ConstantClass.*;
/**
 * Created by Alexander Chuvashov on 10.11.2016.
 */

class TextOperation implements ITextOperation {

    /**
     * Метод проверяет корректность слова через regex выражение и в случае корректности
     * добавляем его в итоговый список и/или увеличиваем количество в списке на единицу
     * @param word - проверяемое слово
     * @return boolean - ture в случае, если слово корректно, false - если слово содержит запрещённые символы
     */
    public boolean checkWord(String word) {
        word = word.toLowerCase();
        word = word.replaceAll(REPLACE_PATTERN, "");
        if (word.length() > 0) {
            if (word.matches(CORRECT_PATTERN)) {
                if (MONITOR.isInterrupted()) {
                    Thread.interrupted();
                }

                Long check = Main.m.get(word);
                Main.m.put(word, check == null ? 1 : check + 1);
                return true;
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
        return new LinkedList<>(Arrays.asList(s.replaceAll("[\n\r\t]"," ").split(" ")));
    }

    /**
     * Метод проверяет строку на наличие латинских символов
     * @param s - строка для проверки
     * @return boolean - ture, если присутствует(ют) латинский символ(ы), false - в ином случае
     */
    public boolean isExistIllegalSymbols(String s) {

        Pattern p = Pattern.compile(ILLEGAL_PATTERN);
        Matcher m = p.matcher(s);
        return m.find();
    }
}

