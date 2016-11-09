package ru.innopolis.reflect.work;

/**
 * Created by Alexander Chuvashov on 09.11.2016.
 */

/*Тестовый класс, создан для того, чтобы можно было увидеть сереализацию/десериализацию объектов в классе*/
class TestObj {
    int a1 = 4;
    int a2 = 5;

    public int getA1() {
        return a1;
    }

    public void setA1(int a1) {
        this.a1 = a1;
    }

    public int getA2() {
        return a2;
    }

    public void setA2(int a2) {
        this.a2 = a2;
    }
}
