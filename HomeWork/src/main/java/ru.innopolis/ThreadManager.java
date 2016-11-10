package ru.innopolis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexander Chuvashov on 10.11.2016.
 */
public class ThreadManager {
    private static Logger logger = LoggerFactory.getLogger(ThreadManager.class);
    List<Thread> threads = new LinkedList<>();

    /**
     * Метод добавляет новый поток в список
     * @param t - новый поток, который необходимо добавить
     */
    public void addThread(Thread t) {
        threads.add(t);
    }

    /**
     * Метод запускает все добавленные потоки
     */
    public void runAllThreads() {
        for(Thread thread: threads) {
            thread.start();
        }
    }

    /**
     * Метод запускает поток с указанным индексом
     * @param i - индекс потока
     */
    public void runThread(int i) {
        threads.get(i).start();
    }

    /**
     * Метод позволяет подождать завершение всех потоков
     * @throws InterruptedException
     */
    public void waitEndThreads() throws InterruptedException {
        for(Thread thread: threads) {
            thread.join();
        }
    }

    /**
     * Метод запускает вывод статистики по кол-ву элементов по указанному интервалу
     * @param milliseconds - интервал для задержки вывода
     * @param m - данные, которые необходимо вывести
     */
    public void showRunStatistic(ThreadMonitor monitor,int milliseconds, Map<String, Long> m) {
        Thread t = new Thread(() -> {
            while (!monitor.isDone() && !monitor.isInterrupted()) {
                try {
                    Thread.sleep(milliseconds);
                    logger.info("Количество ключей в словаре {} шт.", m.size());
                } catch (InterruptedException e) {
                    logger.error("Ошибка прерывания потока! ",e.getStackTrace());
                }
            }
        }
        );
        t.start();
    }

}
