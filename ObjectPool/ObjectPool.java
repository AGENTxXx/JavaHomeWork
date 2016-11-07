package ru.innopolis.course6;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Alexander Chuvashov on 07.11.2016.
 */

/** Класс отвечающий за обработку ошибки */
class PoolException extends Exception {
    /** Поле, указывающее на возниконвение ошибки (при значении true - ошибка произошла) */
    public static volatile boolean isException = false;
    public PoolException(String message){
        super(message);
        isException = true;
    }
}

class ShoesAtr {
    int numShoe;
    long lifeTime;

    ShoesAtr(int numShoe, long lifeTime) {
        this.numShoe = numShoe;
        this.lifeTime = lifeTime;
    }
}

/** Класс обработчик pool-объектов */
class Shoes {

    static List<ShoesAtr> shoes = new LinkedList<>();
    /** Время ожидания пары обуви, если её нет в наличии */
    private static int timeOut = 0;
    /** Количество пар обуви */
    private int allSize = 0;
    /** Время жизни объекта в пуле */
    private static long lifeTime = 0;
    /** Время на которое занимают ботинки */
    private static int busyShoeTime = 0;

    public static int getBusyShoeTime() {
        return busyShoeTime;
    }

    public static void setBusyShoeTime(int busyShoeTime) {
        Shoes.busyShoeTime = busyShoeTime;
    }

    /**
     * Метод отдаёт объект из пула объектов
     * @return Integer - возвращает объект с номером ботинок
     * @throws PoolException - Exception при ожидании свыше указанного времени
     */
    public static Integer getObject() throws PoolException, InterruptedException {
        Integer numShoe;
        int poolSize;

        synchronized (shoes) {
            if (lifeTime != 0) {
                removeFaultyShoes();
            }

            poolSize = shoes.size();
            System.out.println("Size " + poolSize);
            if (poolSize == 0) {

                shoes.wait(timeOut);

                if (poolSize == 0) {
                    /*Исключение может выполняеться 2 раза в связи с тем, что когда выполняется wait 2ой ожидающий
                    * поток так же может дойти до wait. Решил оставить в так, в связи с тем, что эта ситуация
                    * жизненна - когда человек спрашивает есть ли пара, администратор пошёл смотреть, но в это время ещё один
                    * "пользователь" подходит, чтобы узнать об этом, и когда администратор возвращается, то сообщает уже
                    * обоим пользователям о том, что пар нет*/
                    throw new PoolException("Pool объектов был пуст свыше указанного в таймере времени");
                }
            }

            numShoe = shoes.remove(0).numShoe;
            System.out.println("Выдаём " + numShoe + " человеку с номером " + Thread.currentThread().getName());
        }
        return numShoe;
    }

    /**
     * Метод убирает из пула испорченные по времени ботинки
     */
    public static void removeFaultyShoes() {
        boolean isCorrectShoe = false;
        long currentTime = System.currentTimeMillis();

        while (!isCorrectShoe && shoes.size() > 0) {
            if (shoes.get(0).lifeTime < currentTime) {
                System.out.println("Ботинки с номером " + shoes.remove(0).numShoe + " испортились");
            }
            else {
                isCorrectShoe = true;
            }
        }
    }

    /**
     * Метод возвращает обратно объект в пул объектов
     * @param val - номер ботинок
     */
    public static void returnObject(Integer val) {
        synchronized (shoes) {
            if (PoolException.isException) {
                Thread.interrupted();
                return;
            }
            shoes.add(new ShoesAtr(val, System.currentTimeMillis() + lifeTime));
            shoes.notify();
            System.out.println("Человек с именем " + Thread.currentThread().getName() + " вернул ботинки" );
        }
    }

    /**
     * Метод устанавливает кол-во ботинок в наличии
     * @param size - количество ботинок в наличии
     */
    public void setSize(int size) {
        if (allSize < size) {
            for (int i=allSize; i<size; i++) {
                shoes.add(new ShoesAtr(i, System.currentTimeMillis() + lifeTime));
            }

            allSize = size;
        }
    }

    /**
     * Метод устанавливает время жизни элементов в пуле
     * @param lifeTime - время жизни элемента находящегося в пуле
     */
    public void setLifeTime(long lifeTime) {
        this.lifeTime = lifeTime;
    }

    /**
     * Метод устанавливает время ожидания ботинок, если их нет в наличии
     * @param timeOut - время ожидания в миллисекундах
     */
    public void setTimeout(int timeOut) {
        this.timeOut = timeOut;
    }
}

/** Класс эмулирующий работу с пользователем */
class User extends Thread {

    /** Храним номер ботинок */
    Integer numShoe;
    @Override
    public void run() {
        try {
            numShoe = Shoes.getObject();
        } catch (PoolException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(Shoes.getBusyShoeTime());
            Shoes.returnObject(numShoe);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/** Класс по работе с пулом в потоках*/
public class ObjectPool {
    public static void main(String[] args) throws InterruptedException {
        Random rn = new Random();
        Shoes pool = new Shoes();
        pool.setTimeout(1000);
        pool.setLifeTime(2000);
        pool.setBusyShoeTime(2000);
        pool.setSize(5);
        while(!PoolException.isException) {
            Thread t = new Thread(new User());
            t.start();
            Thread.sleep(rn.nextInt(500)+500);
        }
    }
}
