package ru.innopolis.test;

import java.util.List;

/**
 * Created by Alexander Chuvashov on 04.12.2016.
 */
public class Overload {
    public void method(Object o) {
        System.out.println("Object");
    }
    public void method(java.io.FileNotFoundException f) {
        System.out.println("FileNotFoundException");
    }
    public void method(java.io.IOException i) {
        System.out.println("IOException");
    }
    public static void main(String args[]) {
        Overload test = new Overload();
        test.method(null);

        Float f1 = new Float(Float.NaN);
        Float f2 = new Float(Float.NaN);
        System.out.println( ""+ (f1 == f2)+" "+f1.equals(f2)+ " "+(Float.NaN == Float.NaN) );

        List<CharSequence> result;
    }


}

class Mountain {
    static String name = "Himalaya";
    static Mountain getMountain() {
        System.out.println("Getting Name ");
        return null;
    }

    public static void doIt(String String) { //1
        int i = 10;
        i : for (int k = 0 ; k< 10; k++) { //2
            System.out.println( String + i); //3
            if( k*k > 10) continue i; //4
        }
    }

    public static void main(String[ ] args) {
        doIt("33");
        System.out.println( getMountain().name );
        Integer a = 100;
        Integer b = 100;
        Integer c = 128;
        Integer d = 128;
        System.out.println(a==b);
        System.out.println(c==d);
    }
}

class Main2 {
    static void method(int... a) {
        System.out.println("inside int...");
    }
    static void method(long a, long b) {
        System.out.println("inside long");
    }
    static void method(Integer a, Integer b) {
        System.out.println("inside INTEGER");
    }
    public static void main(String[] args) {
        int a = 2;
        int b = 3;
        method(a,b);
    }
}

class Super { static String ID = "QBANK"; }
class Sub extends Super{
    static { System.out.print("In Sub"); }
}
class Test{
    public static void main(String[] args) {
        System.out.println(Sub.ID);

        String s;
        StringBuffer sb;
    }
}

class A{
    protected int i = 10;
    public int getI() { return i; }
}

class B extends A{
    public void process(A a) {
        a.i = a.i*2;
    }
    public static void main(String[] args) {
        A a = new B();
        B b = new B();
        b.process(a);
        System.out.println( a.getI() );
    }
}


class Super2 {
    public String name = "Tort";
    public String getName() {
        return name;
    }

    public void nn() {
        System.out.println("A");
    }
}
class Sub2 extends Super2 {
    public String name = "Habr";
    public void nn() {
        System.out.println("B");
    }
    public static void main(String[] args) {
        Super2 s = new Sub2();
        System.out.println(s.name + " " + s.getName() + " ");
        s.nn();

        /*
        boolean b1 = false;
        boolean b2 = false;
        if (b2 != b1 != !b2) {
            System.out.println("YES");
        }
        else {
            System.out.println("NO");
        }
        */
    }
}

class Test3{
    public static void main(String[] args)   {
        String s = "old";
        print(s = "new", s);
    }
    static void print(String s1, String s2)   {
        System.out.println(s1 +" "+ s2);
    }
}

class A3 {
    A3() {  print();   }
    void print() { System.out.println("A"); }
}
class B3 extends A3 {
    int i = Math.round(3.5f);
    public static void main(String[] args) {
        A3 a = new B3();
        a.print();
    }
    void print() { System.out.println(i); }
}

class Threader extends Thread {
    public void run() {
        System.out.println("In Threader");
    }
}
class Pooler extends Thread {
    public Pooler(){ }
    public Pooler(Runnable r) {
        super( r );
    }
    public void run() {
        System.out.println("In Pooler");
    }
}
class TestClass {
    public static void main(String[] args) {
        Threader t = new Threader();
        Thread h = new Pooler(t);
        h.start();
    }
}

class Habr implements I1, I2{
    public void m() { System.out.println("habr" + I2.VALUE); }
    public static void main(String[] args)   {
        Habr habr = new Habr();
        System.out.println(((I1)habr).VALUE);
        habr.m();

        int i = 40;
        String s = (i < 42) ? "libe" : (i > 50) ? "sfdf" : "dfgf";
    }
}
interface I1{
    int VALUE = 1;
    void m();
}
interface I2{
    int VALUE = 2;
    void m();
}

class Test34{
    private static void md() {

    }
    public static final void main(String[] args) throws Exception   {
        Boolean b = new Boolean("tcbcvnrue");
        int i = 0;
        i++;
        i = i++;
        System.out.println(i);
        b((byte)5);


        /*
        int[] a = null;
        int i = a [ m() ];
        */
    }

    public static void b(byte bb) {

    }

    public static int m() throws Exception   {
        throw new Exception("Another Exception");
    }
}