package ru.innopolis.config;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс констант и универсальных функций
 */
public class Constants {
    /*Настройки для БД */
    public final static String JDBC_DRIVER = "org.postgresql.Driver";
    public final static String JDBC_URL = "jdbc:postgresql://localhost:5432/IS";
    public final static String USER = "postgres";
    public final static String PASSWORD = "";
    /*-----------------*/

    /*Соль для хэша пароля*/
    public final static String SALT = "perm";

    /*Регулярные вырыжения для проверки регистрационных данных*/
    public final static String TEXT_REGEX = "/^[0-9a-zа-яА-ЯёЁA-Z_\\.-]*$/";
    public final static String EMAIL_REGEX = "/^(\\S+)@([a-z0-9-]+)(\\.)([a-z]{2,4})(\\.?)([a-z]{0,4})+$/";
    /*--------------------------------------------------------*/

    public final static String HTML_TAGS = "</?([A-Za-z][A-Za-z0-9]*)\\b";

    /**
     * Метод проверяет на правильность строки по заданному regex-выражению
     * @param value - строка
     * @param regex - regex выражение
     * @return
     */
    public static boolean regexCheck(String value, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(value);
        return m.matches();
    }

    /*Перебираем все совпадения с regex-выражением*/
    public static Iterable<MatchResult> allMatches(
            final Pattern p, final CharSequence input) {
        return new Iterable<MatchResult>() {
            public Iterator<MatchResult> iterator() {
                return new Iterator<MatchResult>() {
                    // Use a matcher internally.
                    final Matcher matcher = p.matcher(input);
                    // Keep a match around that supports any interleaving of hasNext/next calls.
                    MatchResult pending;

                    public boolean hasNext() {
                        // Lazily fill pending, and avoid calling find() multiple times if the
                        // clients call hasNext() repeatedly before sampling via next().
                        if (pending == null && matcher.find()) {
                            pending = matcher.toMatchResult();
                        }
                        return pending != null;
                    }

                    public MatchResult next() {
                        // Fill pending if necessary (as when clients call next() without
                        // checking hasNext()), throw if not possible.
                        if (!hasNext()) { throw new NoSuchElementException(); }
                        // Consume pending so next call to hasNext() does a find().
                        MatchResult next = pending;
                        pending = null;
                        return next;
                    }

                    /** Required to satisfy the interface, but unsupported. */
                    public void remove() { throw new UnsupportedOperationException(); }
                };
            }
        };
    }

    /*Ракрываем все теги при обрезке текста*/
    public static String closeAllTags(String articleText, int lenght) {
        String cropArticle;
        if (lenght >= articleText.length()) {
            return articleText;
        }
        else {
            int closeTaglenght = articleText.indexOf(">", lenght);
            if (closeTaglenght > 0) {
                cropArticle =articleText.substring(0,closeTaglenght+1);
            }
            else {
                cropArticle =articleText.substring(0,lenght);
            }

            List<String> list = new LinkedList<>();
            for (MatchResult match : allMatches(Pattern.compile(HTML_TAGS), cropArticle)) {
                list.add(match.group().substring(1));
            }

            List<String> closeTags = new LinkedList<>();

            for (String s: list) {
                if (s.charAt(0) == '/') {
                    closeTags.remove(closeTags.size()-1);
                }
                else {
                    closeTags.add(s);
                }
            }
            StringBuilder sb = new StringBuilder(cropArticle);

            for(int j=closeTags.size()-1; j>=0; j--) {
                sb.append("</" + closeTags.get(j) + ">");
            }

            return sb.toString();
        }


    }


    /**
     * Функция формирует md5 строку
     * @param md5 - строка, которая должна быть закодирована
     * @return
     */
    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
}
