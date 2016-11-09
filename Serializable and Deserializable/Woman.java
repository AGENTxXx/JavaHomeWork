package ru.innopolis.reflect.work;

/**
 * Created by Alexander Chuvashov on 08.11.2016.
 */

/*Класс показывает, что сериализация/десериализация работает с полями класса родителя*/
public class Woman extends Humann {
    private int breastSize;
    private TestObj t = new TestObj();
    public Woman() {
        super(0,"");
        this.breastSize=0;
    }

    public TestObj getT() {
        return t;
    }

    public Woman(int age, String name, int breastSize) {
        super(age, name);
        this.breastSize = breastSize;
    }
}
