package ru.innopolis.course7;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Chuvashov Alexander on 08.11.2016.
 */

/** Класс отвечающий за генерацию нового числа*/
class numberGenerator extends Thread{
    /*Random переменная позволяющая генерировать следующее число*/
    //Random rn = new Random();
    /*Верхняя граница генерируемого числа*/
    private int lastNumber = 0;
    /*Время задержки между генерацией нового числа*/
    private int timeSleep = 0;

    numberGenerator(int lastNumber, int timeSleep) {
        this.lastNumber = lastNumber;
        this.timeSleep = timeSleep;
    }

    /**
     * Метод позволяет получить новое произволное число не больше lastNumber
     * @return int - новое число
     */
    public int nextValue() {
        return (int)(Math.random()*lastNumber);
    }

    public void run() {
        while (!checkCountNumber.isComplite) {
            checkCountNumber.addNumberInMap(nextValue());
            try {
                Thread.sleep(timeSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/* Класс отвечающий за проверку и занесени новых элементов в пул*/
class checkCountNumber extends Thread {
    /*Переменная отвечающая за то, выполнено ли условия по количеству уникальных объектов*/
    public static boolean isComplite = false;
    /*Время задержки по выводу информации о количестве уникальных компонентов*/
    private int showTimeStatistic = 0;
    /*Необходимое количество уникальных значений в пуле*/
    private int uniqueCount = 0;
    /*Переменная хранящая уникальные значения*/
    private static Map<Integer, Integer> m = new HashMap<>();

    checkCountNumber (int uniqueCount, int showTimeStatistic) {
        this.showTimeStatistic = showTimeStatistic;
        this.uniqueCount = uniqueCount;
    }

    /**
     * Метод позволяющий добавить уникальное значение в наш пул
     * @param num - добавляемое число
     */
    public static void addNumberInMap(int num) {
        Integer check = m.get(num);
        if (check == null) {
            m.put(num, 1);
        }
    }

    /**
     * Метод отображает информацию о количестве уникальных значений
     */
    private void showStatistic() {
        System.out.println("Всего найдено " + m.size() + " уникальных элементов");
    }

    /**
     * Метод проверяет, все ли числа были собраны
     * @return - true, если есть весь набор уникальных значений, иначе false
     */
    public boolean checkFullMap() {
        if (m.size() == uniqueCount) {
            System.out.println("Сгенерированы все " + uniqueCount + " чисел");

            return true;
        }
        return false;
    }

    public void run() {
        while (!isComplite) {
            try {
                Thread.sleep(showTimeStatistic);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            showStatistic();
            isComplite = checkFullMap();
        }
    }
}


public class UniqueNumbers {
    public static void main(String[] args) {
        numberGenerator nm = new numberGenerator(100,1000);
        checkCountNumber cn = new checkCountNumber(100,5000);
        Thread t = new Thread(nm);
        Thread t2 = new Thread(cn);
        t.start();
        t2.start();
    }
}
