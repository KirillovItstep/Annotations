package org.itstep.reflection.intro;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
Недостатки рефлексии:
    Худшая производительность в сравнении с классической работой с классами, методами и переменными;
    Нарушение принципа инкапсуляции.
    Ее нельзя применить при наличии ограничений безопасности. Если мы захотим использовать рефлексию на классе, который защищен с помощью специального класса SecurityManager, ничего не выйдет;
*/

    public class Main {
    public static void main(String[] args) {
        MyClass myClass = new MyClass();
        //Можно только получить значение number
        int number = myClass.getNumber();
        System.out.println("number=" + number);

        //Изменить уровень доступа поля и обратиться к нему
        try {
            Field field = myClass.getClass().getDeclaredField("name");
            /*
            1. getFields() - вернёт все публичные поля класса и всех его родителей по цепочке.
            2. getDeclaredFields() - вернёт все поля класса, не зависимо от модификатора доступа, но не вернёт поля классов-родителей.
            */
            field.setAccessible(true);
            String name = (String) field.get(myClass);
            System.out.println("number=" + number + ", name=" + name);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //Изменить уровень доступа метода и вызвать его
        Method method = null;
        try {
            method = myClass.getClass().getDeclaredMethod("printData");
            method.setAccessible(true);
            method.invoke(myClass);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //Изменить значение поля private
        try {
            Field field = myClass.getClass().getDeclaredField("value");
            field.setAccessible(true);
            //field.set(myClass, "5");
            field.set(myClass, 5);
            method.invoke(myClass);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //Создать экземпляр класса во время выполнения (заранее тип класса неизвестен)
        MyClass myClass2 = null;
        try {
            //Класс класса (специальный класс по имени Class); есть у всех объектов Java
            Class clazz = Class.forName(MyClass.class.getName()); //ClassLoader загрузит нужный класс по имени с помощью метода forName
            //myClass2 = (MyClass) clazz.newInstance();
            myClass2 = (MyClass) clazz.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(myClass2);

        //Вызов конструктора с параметрами приведет к ошибке, поэтому нужно добавить конструктор по умолчанию
        //Вначале конструктор с параметрами закомментирован, а конструктор по умолчанию не описан
        MyClass myClass3 = null;
        try {
            Class clazz = Class.forName(MyClass.class.getName());
            Class[] params = {int.class, String.class, int.class};
            myClass3 = (MyClass) clazz.getConstructor(params).newInstance(1, "default2",3);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(myClass3);

        //Получить все конструкторы класса и параметры к ним
        Class clazz = null;
        try {
            clazz = Class.forName(MyClass.class.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] paramTypes = constructor.getParameterTypes();
            System.out.print("constructor:" + constructor.getName()+", parameters: ");
            for (Class paramType : paramTypes) {
                System.out.print(paramType.getName() + " ");
            }
            System.out.println();
        }
    }
}
