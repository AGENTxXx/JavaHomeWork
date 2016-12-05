package ru.innopolis.test;

import java.io.IOException;

/**
 * Created by Alexander Chuvashov on 04.12.2016.
 */
public class Practice {

    public static void main(String args) {
        Boolean b = new Boolean("/false");
        System.out.println(b == false);
    }
    public static void main(String ... args) {
        Boolean b = new Boolean("/true");
        System.out.println(b);
    }

    public void method() throws IllegalArgumentException,IllegalArgumentException,IOException,IOException {
        int i = 0;
        try {

        }
        catch (Exception e) {

        }
    }

    public static double sqr(double arg) {
        int i = 3;
        while (true);
    }

    public static void main(String args, Integer a) {
        Boolean b = new Boolean("/true");
        System.out.println(b);
    }
}


class b {
    static int b =3;
}

class Testd {

    class A {

        String str= "ab";

        A() {
            printLength();
        }

        void printLength() {
            System.out.println(str.length());
        }
    }

    class BF extends A {
        String str = "abc";

        void printLength() {
            System.out.println(str.length());
        }
    }

    public static void main(String ... args) throws Exception {
        //new Testd().new BF();
        if(System.out.printf("Hello world") == null){
            System.out.println("sd");
        }

        if(System.out.getClass().getMethod("println", String.class).invoke(System.out, "Hello world") == null){
        }

    }
}

class MMM {

    int a = 3;
    int b = 5;

    static String str = "Value 1";

    public static void met(String str) {
        str = "10";
    }

    public static void main(String ... args) throws Exception {
        met(str);
        int i = 5;
        i = i++ + i;
        System.out.println(i);
    }
}