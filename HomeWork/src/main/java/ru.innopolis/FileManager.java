package ru.innopolis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.LinkedList;
import static ru.innopolis.ConstantClass.*;

/**
 * Created by Alexander Chuvashov on 10.11.2016.
 */
public class FileManager implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(FileManager.class);

    /*Количество потоков*/
    private static int threadCount = 0;
    /*Номер потока*/
    private final int threadNum;
    /*Ссылка на файл*/
    private final String fileLink;


    public FileManager(String fileLink) {
        this.fileLink = fileLink;

        synchronized (MONITOR) {
            threadNum =threadCount++;
        }
    }

    /**
     * Метод создаёт соединение и загружает файл в корень, при указании URI-ссылки
     * @param link - URI-ссылка
     * @return String - возвращает локальный путь до файла
     */
    private String downloadFile(String link) throws IOException {
        URLConnection connection;
        String path = "";

        logger.info("Попытка загрузить файл из сети");

        String fileName = link.substring( link.lastIndexOf('/')+1, link.length());
        connection = new URL(link).openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        connection.connect();

        try(ReadableByteChannel rbc = Channels.newChannel(connection.getInputStream()); FileOutputStream fos = new FileOutputStream("./" + fileName); ) {
            path = loadFileAndReturnPath(fileName, rbc, fos);
        } catch (MalformedURLException e) {
            logger.error("Не верный url-адрес!",e);
        }

        return path;
    }

    /**
     * Метод загружает файл fileName и возвращает путь до загруженного локального файла
     * @param fileName - имя зыгружаемого файла
     * @param rbc - ReadableByteChannel
     * @param fos - FileOutputStream
     * @return String - путь до локального загруженного файла
     * @throws IOException
     */
    private String loadFileAndReturnPath(String fileName, ReadableByteChannel rbc, FileOutputStream fos) throws IOException {
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

        logger.info("Файл успешно загружен");
        return "./" + fileName;
    }

    /**
     * Метод использует побитовый буфер с построковым чтением файла.
     * @param file - переменная типа file содержащая информацию о файле, в котором необходимо
     *             подсчитать количесво одинаковых слов
     */
    private void lineMethod(File file) {
        TextOperation fo = new TextOperation();
        try(BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
            String s;
            LinkedList<String> worldList;
            while ((s = in.readLine()) != null) {
                if (fo.isExistIllegalSymbols(s)) {
                    MONITOR.setErrorSettings(fileLink, "Файл " + fileLink + " содержит латинские символы. Выполнение остановленно");
                    return;
                }

                worldList = fo.textWordSplit(s);
                for(int j=0; j<worldList.size(); j++) {
                    String word = worldList.get(j);
                    if (!fo.checkWord(word)) {
                        MONITOR.setErrorSettings(fileLink, "Слово \"" + word + "\" содержит не только кириллические символы!");
                        return;
                    }
                }
            }

            logger.info("Поток с номером {} завершил работу!",threadNum);
        }
        catch (IOException e) {
            MONITOR.setErrorSettings(fileLink, "Произошла ошибка ввода/вывода!", e);
        }
    }

    /**
     * Метод запускаемый в новых созданных потоках, отвечаемый за вычисления в программе
     */
    @Override
    public void run() {
        logger.info("Поток с номером {} запустился!",threadNum);
        File file;
        try {
            if (fileLink.matches(REGEX_URI)) {
                file = new File(downloadFile(fileLink));
            }
            else {
                file = new File(fileLink);
            }
            logger.info("Запускаем обработку файла");
            lineMethod(file);
        } catch (MalformedURLException e) {
            MONITOR.setErrorSettings(fileLink,"URL-адрес " + fileLink + " задан не верно",e);
        } catch (FileNotFoundException e) {
            MONITOR.setErrorSettings(fileLink, "Файл " + fileLink + " не найден. Выполнение остановленно");
        } catch (IOException e) {
            MONITOR.setErrorSettings(fileLink,"Произошла внешняя (через сеть) ошибка ввода/вывода",e);
        }
    }
}
