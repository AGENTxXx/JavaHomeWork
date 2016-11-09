package ru.innopolis.reflect.work;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander Chuvashov on 08.11.2016.
 */

/*Класс отвечает за сериализацию/десериализацию объектов*/
class SerialObject<T> {

    /**
     * Метод добавляет в dom документ (формата xml) новый элемент
     * @param doc - переменная типа Document, содержит созданный DocumentBuilder
     * @param tagName - имя тега
     * @param type - значение, которое необходимо занести в атрибут type
     * @param name - значение, которое необходимо занести в атрибут name
     * @param context - значение, которое необходимо занести в виде контеста внутри создаваемого тега
     * @return
     */
    public Element saveParamInXml(Document doc, String tagName, String type, String name, String context) {
        Element prop = doc.createElement(tagName);
        prop.setAttribute("type", type);
        prop.setAttribute("name", name);
        prop.setTextContent(context);
        return prop;
    }

    /**
     * Метод формирует dom документ (xml формата), которых хранит сериализованные данные
     * об указанном объекте сериализации
     * @param obj - сереализуемый объект или объект, который находится внутри основного сереализуемого объекта
     * @param objClass - класс объекта
     * @param doc - переменная типа Document, содержит созданный DocumentBuilder
     * @param rootElement - переменная типа Element используемая для добавления детей (новых нод) внутрь себя
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    public void valueSets(Object obj, Class objClass, Document doc, Element rootElement) throws IllegalAccessException, ClassNotFoundException {
        Field[] fields = objClass.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.getType().isPrimitive() || f.getType().equals(String.class)) {
                Element prop = saveParamInXml(doc, "property", f.getType().getName(), f.getName(), f.get(obj).toString());
                rootElement.appendChild(prop);
            } else {
                Element prop = doc.createElement("object");
                String className = f.getType().getName();
                prop.setAttribute("type", className);
                rootElement.appendChild(prop);
                objClass = Class.forName(className);
                valueSets(f.get(obj),objClass,doc,prop);
            }
        }
    }

    /**
     * Метод сохраняет указанный объект в указанный файл
     * @param obj - объект
     * @param filename - имя файла
     * @throws IllegalAccessException
     * @throws ParserConfigurationException
     * @throws TransformerException
     * @throws ClassNotFoundException
     */
    public void saveObjectHand(Object obj, String filename) throws IllegalAccessException, ParserConfigurationException, TransformerException, ClassNotFoundException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("object");
        rootElement.setAttribute("type",obj.getClass().getName());
        doc.appendChild(rootElement);

        Class objClass = obj.getClass();
        while (!objClass.equals(Object.class)) {
            valueSets(obj,objClass,doc,rootElement);
            objClass = objClass.getSuperclass();
            if (objClass.getSuperclass() != null) {
                Element prop = doc.createElement("object");
                String className = objClass.getName();
                prop.setAttribute("type", className);
                rootElement.appendChild(prop);
                rootElement = prop;
                objClass = Class.forName(className);

            }
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filename));

        transformer.transform(source, result);
    }

    /**
     * Метод используется для чтения файла
     * @param path - путь к файлу
     * @param encoding - кодировка файла
     * @return - возвращает String с содержимым файла.
     * @throws IOException
     */
    static String readFile(String path, Charset encoding) throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    /**
     * Метод, позволяющий правильно сконвертировать значение поля при записи в объект
     * @param convertType - тип поля
     * @param oldVal - полученное значение при сериализации
     * @return - возвращает переменную нужного типа
     */
    public T typeConvert(String convertType, String oldVal) {
        T value2 = null;
        switch (convertType) {
            case "int":
                value2 = (T)Integer.decode(oldVal) ;
                break;
            case "boolean":
                value2 = (T)Boolean.valueOf(oldVal);
                break;
            case "long":
                value2 = (T)Long.decode(oldVal);
                break;
            case "java.lang.String":
                value2 = (T)oldVal.toString();
                break;
        }
        return value2;
    }

    /**
     * Формируем источник данных (HashMap) при десериализации из файла
     * @param root - основной нод объекта, который внутри себя содержит свойства
     * @param attributeHashMap - объект хранения данных
     */
    public void valueGets(Node root, Map<MapKeyClass,Atribute> attributeHashMap) {
        String objectClass = root.getAttributes().getNamedItem("type").getNodeValue();

        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.hasAttributes()) {
                if (node.getNodeName() == "property") {
                    String atrName = node.getAttributes().getNamedItem("name").getTextContent();
                    String atrType = node.getAttributes().getNamedItem("type").getTextContent();
                    String value = node.getTextContent();
                    attributeHashMap.put(new MapKeyClass(objectClass,atrName),new Atribute(atrType,atrName,value));
                }
                else {
                    valueGets(node,attributeHashMap);
                }
            }
        }
    }

    /**
     * Метод заполняет десериализованными данными объект, который должен будет возвратиться при полной десериализации
     * @param o - сам объект (может быть как главным/основным, так и вложенным в основной объект
     * @param objClass - класс объекта
     * @param attributeHashMap - переменная хранящая десериализованные данные из файла
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    public void recreationFacility(Object o, Class objClass, Map<MapKeyClass,Atribute> attributeHashMap) throws IllegalAccessException, ClassNotFoundException {
        Field[] fields = objClass.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.getType().isPrimitive() || f.getType().equals(String.class)) {
                f.set(o, typeConvert(f.getType().getName(), attributeHashMap.get(new MapKeyClass(f.getDeclaringClass().getName(),f.getName())).atributeValue));
            }
            else {
                String className = f.getType().getName();
                objClass = Class.forName(className);
                recreationFacility(f.get(o), objClass,attributeHashMap);
            }
        }
    }

    /**
     Метод восстанавливает и отдаёт объект из файла (в данном методе происходит десериализация
     * @param filename - имя файла, который необходимо десериализовать
     * @return - возвращает объект
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public T loadObjectHand(String filename) throws IOException, ParserConfigurationException, SAXException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Map<MapKeyClass,Atribute> attributeHashMap = new HashMap<>();
        String s = readFile(filename, Charset.defaultCharset());
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        // Создается дерево DOM документа из файла
        Document document = documentBuilder.parse(filename);

        // Получаем корневой элемент
        Node root = document.getDocumentElement();
        valueGets(root,attributeHashMap);

        Class objClass = Class.forName(root.getAttributes().getNamedItem("type").getNodeValue());

        Constructor[] constructors = objClass.getConstructors();
        Object o = constructors[0].newInstance();
        while (!objClass.equals(Object.class)) {
            recreationFacility(o,objClass,attributeHashMap);
            objClass = objClass.getSuperclass();
        }
        return (T)o;
    }
}