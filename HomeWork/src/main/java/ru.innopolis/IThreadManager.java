package ru.innopolis;

import java.util.Map;

/**
 * Created by Alexander Chuvashov on 12.11.2016.
 */
public interface IThreadManager {
    void addThread(Thread t);
    void runAllThreads() throws InterruptedException;
    void showRunStatistic(int milliseconds, Map<String, Long> m);

}
