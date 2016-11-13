package ru.innopolis;

import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;
import static ru.innopolis.ConstantClass.*;
/**
 * Created by Alexander Chuvashov on 11.11.2016.
 */
public class ThreadMonitorTest {

    @Test
    public void testSetDone() {
        MONITOR.setDone(true);
        assertTrue("setDone work incorrect", MONITOR.isDone() == true);
    }

    @BeforeClass
    public static void setClassValue() {
        MONITOR.setErrorSettings("e:\\file.txt", "Ошибка ввода/вывода", new RuntimeException());
    }

    @Test
    public void testFileNameError() {
        assertTrue("FileName incorrect", MONITOR.getFileError() == "e:\\file.txt");
    }


    @Test
    public void testErrorInterrupted() {
        assertTrue("ErrorInterrupted", MONITOR.getErrorInterrupted() == "Ошибка ввода/вывода");
    }

    @Test
    public void testIsInteruppted() {
        assertTrue("Error isInterrupted", MONITOR.isInterrupted() == true);
    }

    @Test(expected = RuntimeException.class)
    public void testErrorException() throws Exception {
        throw MONITOR.getErrorExeption();
    }
}
