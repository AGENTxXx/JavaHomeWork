package ru.innopolis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static ru.innopolis.ConstantClass.*;
/**
 * Created by Chuvashvo Alexander on 04.11.2016.
 */

public class Main {

    /*Храним количество повторений слов в проверяемых файлах*/
    static Map<String, Long> m = new ConcurrentHashMap<>();
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args)  {
        ThreadManager threadManager = new ThreadManager();

        if (args.length == 0) {
            logger.info("Загружаемые файлы не указаны!");
            return;
        }

        for (String arg: args) {
            threadManager.addThread(new Thread(new FileManager(arg)));
        }

        threadManager.showRunStatistic(100,m);

        try {
            threadManager.runAllThreads();
        } catch (InterruptedException e) {
            logger.error("Trace \r\n {}",MONITOR.getErrorExeption());
        }

        if (MONITOR.isInterrupted()) {
            logger.error(MONITOR.getErrorInterrupted());
            logger.error(MONITOR.getFileError());
            if (MONITOR.getErrorExeption() != null) {
                logger.error("Trace \r\n {}",MONITOR.getErrorExeption());
            }

        }
        else {
            logger.info("Полученный результат \r\n{}", m);
            MONITOR.setDone(true);
        }
    }
}
