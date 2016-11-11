package ru.innopolis;

/**
 * Created by Alexander Chuvashov on 12.11.2016.
 */
public interface IThreadMonitor {
    boolean isDone();
    void setDone(boolean done);
    void setErrorSettings(String fileError, String errorInterrupted);
    void setErrorSettings(String fileError, String errorInterrupted, Exception errorException);
    String getErrorInterrupted();
    boolean isInterrupted();
    String getFileError();
    Exception getErrorExeption();
}
