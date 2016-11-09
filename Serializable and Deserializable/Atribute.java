package ru.innopolis.reflect.work;

/**
 * Created by Alexander Chuvashov on 08.11.2016.
 */

/*Класс хранимых атрибутов*/
class Atribute {
    /*Тип атрибута*/
    public String atributeType;
    /*Имя атрибута*/
    public String atributeName;
    /*Значение атрибута*/
    public String atributeValue;

    Atribute(String atributeType, String atributeName, String atributeValue) {
        this.atributeType = atributeType;
        this.atributeName = atributeName;
        this.atributeValue = atributeValue;
    }

}