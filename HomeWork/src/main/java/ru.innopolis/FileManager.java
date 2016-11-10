package ru.innopolis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.util.LinkedList;

/**
 * Created by Alexander Chuvashov on 10.11.2016.
 */
public class FileManager implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(FileManager.class);

    private static int threadCount = 0;
    private final int threadNum;
    private long bufferSize;
    private final String fileLink;
    private final String pattern;
    private final String illegalPattern;
    ThreadMonitor monitor;


    public FileManager(ThreadMonitor monitor, String fileLink, String pattern, String illegalPattern) {
        this.fileLink = fileLink;
        this.monitor = monitor;
        this.pattern = pattern;
        this.illegalPattern = illegalPattern;

        synchronized (monitor) {
            threadCount++;
            threadNum =threadCount;
        }
    }

    public FileManager(ThreadMonitor monitor, String fileLink, String pattern, String illegalPattern, long bufferSize) {
        this(monitor,fileLink,pattern,illegalPattern);
        this.bufferSize = bufferSize;
    }

    public String downloadFile(String link) {
        URLConnection connection;
        String path = "";

        logger.info("Попытка загрузить файл из сети");

        String fileName = link.substring( link.lastIndexOf('/')+1, link.length());
        try {
            connection = new URL(link).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();

            try(ReadableByteChannel rbc = Channels.newChannel(connection.getInputStream()); FileOutputStream fos = new FileOutputStream("./" + fileName); ) {

                //rbc = Channels.newChannel(website.openStream());
                //fos = new FileOutputStream("./" + fileName);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

                logger.info("Файл успешно загружен");
                path ="./" + fileName;
            } catch (MalformedURLException e) {
                logger.error("Не верный url-адрес!",e);
            } catch (FileNotFoundException e) {
                logger.error("Файл не найден! ",e);
            }

        } catch (IOException e) {
            logger.error("Произошла внешняя (через сеть) ошибка ввода/вывода",e);
        }

        return path;
    }

    public String getFile(String link) throws FileNotFoundException {
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        if (link.matches(regex)) {
            String filepath = downloadFile(link);
            if (filepath != "") {
                return filepath;
            }
            else {
                throw new FileNotFoundException();
            }
        }
        else {
            return link;
        }
    }

    /**
     * Метод использует побитовый буфер с построковым чтением файла.
     * @param file - переменная типа file содержащая информацию о файле, в котором необходимо
     *             подсчитать количесво одинаковых слов
     * @param pattern - содержит паттерн для проверки символов, которые могут находится в слове
     */
    public void lineMethod(File file, String pattern, String illegalPattern) {
        FileOperation fo = new FileOperation(monitor,pattern);
        try(BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
            String s;
            LinkedList<String> worldList;
            while ((s = in.readLine()) != null) {
                if (fo.isExistIllegalSymbols(s,illegalPattern)) {
                    monitor.setErrorSettings(fileLink, "Файл " + fileLink + " содержит латинские символы. Выполнение остановленно");
                    return;
                }

                worldList = fo.textWordSplit(s);
                for(int j=0; j<worldList.size(); j++) {
                    String word = worldList.get(j);
                    if (!fo.checkWord(word)) {
                        monitor.setErrorSettings(fileLink, "Слово \"" + word + "\" содержит не только кириллические символы!");
                        return;
                    }
                }
            }

            logger.info("Поток с номером {} завершил работу!",threadNum);
        }
        catch (IOException e) {
            monitor.setErrorSettings(fileLink, "Произошла ошибка ввода/вывода!", e);
        }
    }

    /**
     * Метод использует побитовый буфер с указанием его размера. Данный метод позволяет эффективнее
     * работать с большими размерами файла
     * @param file - переменная типа file содержащая информацию о файле, в котором необходимо
     *             подсчитать количесво одинаковых слов
     * @param pattern - содержит паттерн для проверки символов, которые могут находится в слове
     */
    public void bytesMethod(File file, String pattern, String illegalPattern) {
        int shiftBuffer = 0;
        long lastPos = 0;
        int lastIteration = 0;
        boolean lastSymbolIsSpace = false;
        LinkedList<String> worldList;
        CharBuffer charBuffer;
        MappedByteBuffer buffer;
        String partText;
        String charEncoding = System.getProperty("file.encoding");
        long lastSpacePosition;
        ExtendFileOperation fo = new ExtendFileOperation(monitor,pattern);

        try (FileChannel fileChannel = new RandomAccessFile(file, "r").getChannel();) {
            while (lastPos < fileChannel.size()) {
                if (lastPos + bufferSize > fileChannel.size()) {
                    lastIteration = 1;
                    buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, lastPos, fileChannel.size() - lastPos);
                } else {
                    buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, lastPos, bufferSize);
                }

                lastSymbolIsSpace = false;
                shiftBuffer = 0;

                partText = Charset.forName(charEncoding).decode(buffer).toString();
                if (fo.isExistIllegalSymbols(partText, illegalPattern)) {
                    //System.out.println("Файл содержит латинские символы. Выполнение остановленно");
                    monitor.setErrorSettings(fileLink, "Файл " + fileLink + " содержит латинские символы. Выполнение остановленно");
                    return;
                }

                lastSpacePosition = fo.lastSpacePosition(partText);
                if (lastSpacePosition == partText.length() - 1) {
                    lastSymbolIsSpace = true;
                }
                worldList = fo.textWordSplit(partText);

                for (int j = 0; j < (worldList.size() - 1) + lastIteration; j++) {
                    String s = worldList.get(j);
                    if (!fo.checkWord(s)) {
                        monitor.setErrorSettings(fileLink, "Слово \"\" + s + \"\" содержит не только кириллические символы!");
                        return;
                    }
                }

                if (lastIteration == 0) {
                    String s = worldList.get(worldList.size() - 1);
                    if (!fo.checkCorrectLastSymbol(s)) {
                        //System.out.println("Слово " + s);
                        shiftBuffer = s.getBytes().length - 2;
                    } else {
                        shiftBuffer = s.getBytes().length;
                        if (lastSymbolIsSpace) {
                            shiftBuffer++;
                        }
                    }
                }

                lastPos += bufferSize - shiftBuffer;
            }
            logger.info("Поток с номером {} завершил работу!",threadNum);
        } catch (IOException e) {
            monitor.setErrorSettings(fileLink, "Произошла ошибка ввода/вывода!", e);
        }
    }

    @Override
    public void run() {
        logger.info("Поток с номером {} запустился!",threadNum);

        File file = null;
        try {
            file = new File(getFile(fileLink));
        } catch (FileNotFoundException e) {
            monitor.setErrorSettings(fileLink, "Файл " + fileLink + " не найден. Выполнение остановленно");
            return;
        }

        /*
        if (!file.exists()) {
            monitor.setErrorSettings(fileLink, "Файла по адресу \"" + fileLink + "\" не существует!");
            return;
        }
        */

        if (bufferSize == 0) {
            logger.info("Используем простой метод");
            lineMethod(file, pattern, illegalPattern);
        }
        else {
            logger.info("Используем сложный метод");
            bytesMethod(file, pattern, illegalPattern);
        }

    }
}
