package ru.innopolis;

/**
 * Created by Alexander Chuvashov on 10.11.2016.
 */
class ThreadMonitor {
    private boolean interrupted = false;
    private String errorInterrupted;
    private String fileError;
    private Exception errorExeption;
    private boolean done = false;

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }


    /**
     * Метод устанавливает переменные, которые содержат причину прерывания потока, а так же
     * устанавливает указатель на то, чтобы все отсальные вычислительные потоки прервались
     * @param fileError - путь до ошибочного файла
     * @param errorInterrupted - причина ошибки/перывания
     * @param errorExeption - объект системной ошибки
     */
    public void setErrorSettings(String fileError, String errorInterrupted, Exception errorExeption) {
        this.fileError = fileError;
        this.errorInterrupted = errorInterrupted;
        this.errorExeption = errorExeption;
        interrupted = true;
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
        interrupted = true;
    }

    /**
     * Метод возвращает ошибку прерывания
     * @return String
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
     * @return String
     */
    public String getFileError() {
        return fileError;
    }

    /**
     * Метод возвращает системную ошибку
     * @return Exception
     */
    public Exception getErrorExeption() {
        if (errorExeption != null) {
            return errorExeption;
        }
        else {
            return null;
        }

    }
}
