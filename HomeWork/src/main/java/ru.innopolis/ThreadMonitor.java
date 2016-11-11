package ru.innopolis;

import static ru.innopolis.ConstantClass.*;
/**
 * Created by Alexander Chuvashov on 10.11.2016.
 */
class ThreadMonitor implements IThreadMonitor {

    private volatile boolean interrupted = false;
    private String errorInterrupted;
    private String fileError;
    private Exception errorException;
    private boolean done = false;

    /**
     * Метод проверяет, завершились ли все вычислительные потоки
     * @return true, если потоки завершились, false - если ещё не завершились
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Метод устанавливает значение указывающее на завершенность всех вычислительных потоков
     * @param done - true, если все вычислительные потоки завершиилсь, false - в противном случае
     */
    public void setDone(boolean done) {
        this.done = done;
    }


    /**
     * Метод устанавливает переменные, которые содержат причину прерывания потока, а так же
     * устанавливает указатель на то, чтобы все отсальные вычислительные потоки прервались
     * @param fileError - путь до ошибочного файла
     * @param errorInterrupted - причина ошибки/перывания
     */
    public void setErrorSettings(String fileError, String errorInterrupted) {
        this.fileError = fileError;
        this.errorInterrupted = errorInterrupted;
        synchronized (MONITOR) {
            this.interrupted = true;
        }
    }

    /**
     * Метод устанавливает переменные, которые содержат причину прерывания потока, а так же
     * устанавливает указатель на то, чтобы все отсальные вычислительные потоки прервались
     * @param fileError - путь до ошибочного файла
     * @param errorInterrupted - причина ошибки/перывания
     * @param errorException - объект системной ошибки
     */
    public void setErrorSettings(String fileError, String errorInterrupted, Exception errorException) {
        setErrorSettings(fileError,errorInterrupted);
        this.errorException = errorException;
    }

    /**
     * Метод возвращает ошибку прерывания
     * @return String - переменная с пояснением произошедшей ошибки
     */
    public String getErrorInterrupted() {
        return errorInterrupted;
    }

    /**
     * Метод возвращает значение, указываующее на необходимость прервать потоки
     * @return boolean - если true, то необходимо прервать потоки, иначе false
     */
    public boolean isInterrupted() {
        return interrupted;
    }

    /**
     * Метод возвращает пудь до файла
     * @return String - переменная с путём файла
     */
    public String getFileError() {
        return fileError;
    }

    /**
     * Метод возвращает программную ошибку или null, если ошибки не было
     * @return Exception - программная ошибка при вычислении
     */
    public Exception getErrorExeption() {
        if (errorException != null) {
            return errorException;
        }
        else {
            return null;
        }

    }
}
