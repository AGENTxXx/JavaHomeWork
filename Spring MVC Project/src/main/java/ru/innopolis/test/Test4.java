package ru.innopolis.test;

import ru.innopolis.test.test2.Test1;

/**
 * Created by Alexander Chuvashov on 07.12.2016.
 */
public class Test4 {
    public static void main(String[] args) {
        Test1 t1 = new Test1();
        //t1.m();
        Test5 test5 = new Test5("dd");
        //System.out.println(Test5.MD412.G());
    }

}

class Test41 {
    public static void m() {
        System.out.println("m");
    }
}

class Test5 extends Test41{
    private Test5() {
    }

    protected Test5(String s) {

    }

    protected Test5(String s,String s2) {

    }

    protected Test5(String... s) {

    }

    public static class MD412 {
        int d = 4;
        public void G() {
            System.out.println("go go go!");
        }
    }


    public void m(int d) {
        System.out.println("fdgd");
    }

}

class M1 {
    int b = 45;
}

interface Shape {
    public static void draw() {
        System.out.println("Wow! It is impossible!");
    };
}

class OuterClass {
    public static final int mdds;
    static
    {
        mdds = 4;
    }
    public OuterClass() {

    }

    public void method() {
        System.out.println("F");
    }

    interface Dg {
        int d = 4;
        int con();
    }

    public class InnerClass implements Dg {
        public InnerClass() {

        }
        public void method() {
            System.out.println("M");
        }

        public void anotherMethod() {
            OuterClass.this.method();
        }

        @Override
        public int con() {
            return 0;
        }
    }

    class A {
        int c = 4;
    }

    static final int mda;
    static
    {
        mda = 5;
    }

    public static void main(String[] args) {

        int anar[] = new int[5];

        System.out.println(anar[0]);

        /*
        OuterClass outerClass = new OuterClass();
        InnerClass ic = outerClass.new InnerClass();
        ic.method();
        ic.anotherMethod();
        M1 m1 = new M1();
        Shape.draw();
        String s = "ABCDEFDCC".replace("C", "**");
        System.out.println(s.intern());

        String s1 = "Cat";
        String s2 = "Cat";
        String s3 = new String("Cat");
        String s4 = new String("Cat").intern();

        System.out.println("s1 == s2 :"+(s1==s2)); //s1 == s2 :true
        System.out.println("s1 == s3 :"+(s1==s3)); //s1 == s3 :false
        System.out.println("s1 == s3 :"+(s1==s4)); //s1 == s3 :false

        String wordsWithNumbers = "I|am|a|java|developer";
        String[] numbers = wordsWithNumbers.split("\\|");
        System.out.println(numbers.length);
        */
    }
}


class Class1 {
    Class1(int i) {
        System.out.println("Class1(int)");
    }
    Class1() {
        System.out.println("Class1(int)");
    }
}

class Class2 extends Class1 {



    Class2(int fg) {
        super(fg);// 2
        System.out.println("Class2(int)");
    }

    Class2(double d) {              // 1
        this((int) d);
        System.out.println("Class2(double)");
    }

    public static void main(String[] args) {
        new Class2(0.0);
    }
}

class AA { }
class BA extends AA { }

class MainA {
    public static void main (String [] args) {
        AA a = new AA();
        BA b = (BA) a;
        System.out.println(a == b);
    }
}