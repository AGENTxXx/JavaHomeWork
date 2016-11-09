package ru.innopolis.reflect.work;

/**
 * Created by Alexander Chuvashov on 08.11.2016.
 */

/*Класс формирует ключ при десериализации, для того, чтобы правильно установить значение полей*/
public class MapKeyClass {
    String className;
    String variable;


    public MapKeyClass(String className, String variable) {
        this.className = className;
        this.variable = variable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MapKeyClass that = (MapKeyClass) o;

        if (className != null ? !className.equals(that.className) : that.className != null) return false;
        return variable != null ? variable.equals(that.variable) : that.variable == null;

    }

    @Override
    public int hashCode() {
        int result = className != null ? className.hashCode() : 0;
        result = 31 * result + (variable != null ? variable.hashCode() : 0);
        return result;
    }
}
