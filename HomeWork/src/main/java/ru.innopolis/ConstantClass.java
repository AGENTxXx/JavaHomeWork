package ru.innopolis;

/**
 * Created by Alexander Chuvashov on 12.11.2016.
 */

/*Класс констант*/
public final class ConstantClass {
    /*Regex выражение с помощью которого проверяется, является ли строка URI-строкой*/
    public static final String REGEX_URI = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    /*Regex выражение указывающее на символы, которые необходимо изключить из строки*/
    public static final String REPLACE_PATTERN = "(?U)[\\pP\\s\\d\\�\\<\\>\\№\\-\\+]";
    /*Regex выражение указывающее на символы, которые могут находиться в строке*/
    public static final String CORRECT_PATTERN = "[а-яё]*";
    /*Regex выражение указывающее на символы, которые не могут находиться в строке*/
    public static final String ILLEGAL_PATTERN = "[a-zA-Z]";
    /*Монитор для выполения синхронных опираций и прерыванию потоков при ошибке*/
    public static final ThreadMonitor MONITOR = new ThreadMonitor();
}
