package ru.innopolis.reflect.work;

import org.xml.sax.SAXException;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import java.lang.reflect.InvocationTargetException;


/**
 * Created by Alexander Chuvashov on 08.11.2016.
 */

public class Main {

    public static void main(String[] args) throws IOException, IllegalAccessException, ParserConfigurationException, SAXException, ClassNotFoundException, InvocationTargetException, InstantiationException, TransformerException {
        SerialObject so = new SerialObject();
        /*Блок сериализации объекта*/
        Woman wm = new Woman(27,"Sahra",2);
        wm.getT().setA1(12);
        wm.getT().setA2(17);
        so.saveObjectHand(wm,"text2.xml");
        /*Конец блока*/

        /*Десериализация объекта*/
        Woman wm2 = (Woman) so.loadObjectHand("text2.xml");

    }

}
