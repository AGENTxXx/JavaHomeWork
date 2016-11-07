package ru.innopolis.course6;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Alexander Chuvashov on 07.11.2016.
 */

/** Класс реализует ситуацию deadlock*/
class Dlock implements Runnable {

    /** Первый ресурс */
    String r1;
    /** Второй ресурс */
    String r2;

    /** Первая блокировка */
    Lock l1;
    /** Вторая блокировка */
    Lock l2;

    Dlock(String r1, String r2, Lock l1, Lock l2) {
        this.r1 = r1;
        this.r2 = r2;
        this.l1 = l1;
        this.l2 = l2;
    }

    @Override
    public void run() {
        try {
            l1.lock();
            r1 += "10";
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            l2.lock();
            r2 += "20";
            System.out.println(r1+r2);
        }
        finally {
            l2.unlock();
            l1.unlock();
        }
    }
}

/** Класс запускающий реализацию deadlock*/
public class DeadLock {
    public static void main(String[] args) {

        String  r1 = "";
        String r2 = "";
        Lock lock = new ReentrantLock();
        Lock lock2 = new ReentrantLock();

        Thread t = new Thread(new Dlock(r1,r2,lock,lock2));
        Thread t2 = new Thread(new Dlock(r2,r1,lock2,lock));

        t.start();
        t2.start();
    }
}
