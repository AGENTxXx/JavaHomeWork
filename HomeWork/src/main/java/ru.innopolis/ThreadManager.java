package ru.innopolis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static ru.innopolis.ConstantClass.*;
/**
 * Created by Alexander Chuvashov on 10.11.2016.
 */

/*Класс управляющий потоками*/
public class ThreadManager implements IThreadManager {
    private static Logger logger = LoggerFactory.getLogger(ThreadManager.class);
    private ExecutorService service;
    List<Thread> threads = new LinkedList<>();

    /**
     * Метод добавляет новый поток в список
     * @param t - новый поток, который необходимо добавить
     */
    public void addThread(Thread t) {
        threads.add(t);
    }

    /**
     * Метод запускает все добавленные потоки и ожидает их завершение
     * @throws InterruptedException
     */
    public void runAllThreads() throws InterruptedException {
        service = Executors.newFixedThreadPool(threads.size());
        List<Callable<Object>> todo = new ArrayList<>(threads.size());
        for(Thread thread: threads) {
            todo.add(Executors.callable(thread));
        }
        service.invokeAll(todo);
        service.shutdown();
    }

    /**
     * Метод запускает вывод статистики по кол-ву элементов по указанному интервалу
     * @param milliseconds - интервал для задержки вывода
     * @param m - данные, которые необходимо вывести
     */
    public void showRunStatistic(int milliseconds, Map<String, Long> m) {
        Thread t = new Thread(() -> {
            while (!MONITOR.isDone() && !MONITOR.isInterrupted()) {
                try {
                    Thread.sleep(milliseconds);
                    logger.info("Количество ключей в словаре {} шт.", m.size());
                } catch (InterruptedException e) {
                    logger.error("Ошибка прерывания потока! ",e);
                }
            }
        }
        );
        t.start();
    }

}
