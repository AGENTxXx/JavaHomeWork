package ru.innopolis.tesdt;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.innopolis.config.Constants.closeAllTags;

/**
 * Created by Alexander Chuvashov on 02.12.2016.
 */
public class Main {
    public static int LOOP_COUNT = 1_000_000;



    public static void main(String[] args) throws InterruptedException {
        /*
        List<String> list = new ArrayList<>();
        for(int i = 0; i < LOOP_COUNT; i++) {
            Random r = new Random();
            String s = r.nextInt() + " " + i;
            list.add(s);
            System.out.println(s);
            Thread.sleep(1);
        }
        System.out.println(list.size());
        */

        String text = "<h4 class=\"h4\">How to create Singleton design pattern?</h4>\n" +
                "<p>To create the singleton class, we need to have static member of class, private constructor and static factory method.</p>\n" +
                "\n" +
                "<ul><li><b>Static member:</b> It gets memory only once because of static, itcontains the instance of the Singleton class.</li><li><b>Private constructor:</b> It will prevent to instantiate the Singleton class from outside the class.</li><li><b>Static factory method:</b> This provides the global point of access to the Singleton object <b><i>and returns the instance";



        System.out.println(closeAllTags(text, text.length()));



        /*
        Regex rx = new Regex("^<.*>\\b");
        Matcher m = Pattern.compile("^<.*>\\b").matcher(text);
        while (m.find()) {
            System.out.println("Found: " + m.group(0));
        }


        Pattern p = Pattern.compile("^<.*>\\b");
        Matcher m2 = p.matcher(text);
        boolean b = m2.matches();
        System.out.println(m2.group(0));
        */

        /*
        try(Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNext()) {
                createObject();
                scanner.next();
            }
        }
        */
    }

    static Object createObject() throws InterruptedException {
        List<String> list = new ArrayList<>();
        for(int i = 0; i < LOOP_COUNT; i++) {
            Random r = new Random();
            String s = r.nextInt() + " " + i;
            list.add(s);
        }
        return list;
    }
}
