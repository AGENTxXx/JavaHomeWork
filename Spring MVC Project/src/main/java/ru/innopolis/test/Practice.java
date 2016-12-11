package ru.innopolis.test;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

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

class Testd {

    static class A extends B {
        static Integer q = 2;
        static {
            System.out.print("A");
            A.q = 4;
        }
    }

    static class B {
        static {
            System.out.print("B");
            A.q++;
        }
    }

    public static void main(String[] args) {
        System.out.println(A.q);
    }
}


interface AE {
    Integer a();
}
interface BE {
    Number a();
}


class C implements AE, BE {
    public Integer a() {
        return null;
    }
}
class Overloads {
    public static void main(String[] args) {
        Testf t=new Testf();
        t.clones();
        System.out.println(t.i);
    }
}
class Testf{
    int i;
    public void clones(){
        i=15;
    }
}

class MM {
    public final int d = 4;

    public final void Aa() {

    }
}

class MM2 extends Observable {


    public static void main(String[] args) {
        int i, n = 10;
        for (i = 0; i < n; n--) {
            System.err.println("*");
        }
    }
}