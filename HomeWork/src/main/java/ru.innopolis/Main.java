package ru.innopolis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Chuvashvo Alexander on 04.11.2016.
 */




public class Main {
    static Map<String, Long> m = new HashMap<>();
    private static Logger logger = LoggerFactory.getLogger("ru.innopolis.Main");

    public static void main(String[] args)  {
        //List<String> paths = new ArrayList<>();
        ThreadManager tm = new ThreadManager();
        ThreadMonitor monitor = new ThreadMonitor();

        String pattern = "[а-яё]*";
        String illegalPattern = "[a-zA-Z]";


        if (args.length == 0) {
            logger.info("Загружаемые файлы не указаны!");
            return;
        }

        for (String arg: args) {
            //tm.addThread(new Thread(new FileManager(monitor,arg,pattern,illegalPattern,10000)));
            tm.addThread(new Thread(new FileManager(monitor,arg,pattern,illegalPattern)));
        }

        tm.runAllThreads();
        tm.showRunStatistic(monitor,100,m);
        try {
            tm.waitEndThreads();
        } catch (InterruptedException e) {
            logger.error("Ошибка прерывания потока!", e.getStackTrace());
        }

        if (monitor.isInterrupted()) {
            logger.error(monitor.getErrorInterrupted());
            logger.error(monitor.getFileError());
            logger.error("Trace \r\n {}",monitor.getErrorExeption());
        }
        else {
            logger.info("Полученный результат \r\n{}", m);
            monitor.setDone(true);
        }
    }
}
