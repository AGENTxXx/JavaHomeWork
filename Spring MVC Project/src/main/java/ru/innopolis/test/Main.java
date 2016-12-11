package ru.innopolis.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.dao.PostgreSqlUserDao;

import java.io.FilterInputStream;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static ru.innopolis.config.Constants.closeAllTags;

/**
 * Created by Alexander Chuvashov on 02.12.2016.
 */
public class Main {
    public static int LOOP_COUNT = 1_000_000;

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private final int i = 3;

    HashSet d;
    public Main() {
        int a = new Integer(1);
    }


    public static void main(String... args) throws InterruptedException {

        int a = 128;
        byte b = (byte)a;
        a = -127;
        b = (byte)a;
        byte v = 3;
        byte g = 5;
        Object c = v + g;
        System.out.println(c.getClass());
        System.out.println(-0b111111>>>1);
        logger.info("Info test!");
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

        /*
        String text = "<h4 class=\"h4\">How to create Singleton design pattern?</h4>\n" +
                "<p>To create the singleton class, we need to have static member of class, private constructor and static factory method.</p>\n" +
                "\n" +
                "<ul><li><b>Static member:</b> It gets memory only once because of static, itcontains the instance of the Singleton class.</li><li><b>Private constructor:</b> It will prevent to instantiate the Singleton class from outside the class.</li><li><b>Static factory method:</b> This provides the global point of access to the Singleton object <b><i>and returns the instance";



        System.out.println(closeAllTags(text, text.length()));

    */

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


class Superclass {
    public int field;
    public int getField() { return field; }
    public void setField(int value) { field = value; }
}

class Subclass extends Superclass{
    public int field;
    public int getField() { return field; }
    public void setField(int value) { field = value; }
    public static void main(String[] params) {
        Superclass sup = new Subclass();
        sup.field = 10;
        System.out.println(sup.getField());
        int x = 0xFFD;
    }
}

class N implements n1,n12 {

    @Override
    public int mm() {
        return 5;
    }
}

interface n1 {
    default int mm() {
        return 4;
    }
}

interface n12 {
    default int mm() {
        return 6;
    }
}

class LambdaTest {
    public static void main(String[] args) {
        //new Thread(()-> System.out.println("1")).start();
        /*
        int i =0;

        test(() -> {
            System.out.println(i); //Но можем передать ссылку и ниже работать с тем же объектом
        });

        i = 10;
        */

        /*
        test(new FunctionalInterface() {
            @Override
            public void doSome() {
                System.out.println("SUCCESS");
            }
        });
        */

        //Testt t = new Testt();

        /*test(new FunctionalInterface() {
            Testt delegate = new Testt();
            @Override
            public void doSome() {
                delegate.print();
            }
        });*/

        /*
        Predicate p;
        Function f;

        test((s2) -> {
            t.print(s2);
        });

        test(t::print);
        */

        Collection<Integer> collection
                = Arrays.asList(new Integer[]{1,2,3,4,5,6});

        Integer val = collection.stream().filter((arg)->arg % 2 != 0).reduce((s1,s2)->s1+s2).orElse(0);

        int count=0;
        for(Integer i :collection) {
            count +=(i%2 == 0) ? i:0;
            if (i%2 == 0) {
                count+=i;
            }
        }

        System.out.println(val);
    }

    public static void test(FunctionalInterface functionalInterface) {
        functionalInterface.doSome("ddd");
    }
}

class Testt {
    void print(String s) {
        System.out.println(s);
    }
}

@java.lang.FunctionalInterface
interface FunctionalInterface {
    void doSome(String s);

    int hashCode();

    default void doSomeDefault() {
        int i = hashCode();
        System.out.println(i);
    }

    //void someElseAbstract();
}
