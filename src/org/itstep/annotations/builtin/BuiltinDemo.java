package org.itstep.annotations.builtin;

import java.util.HashMap;
import java.util.Map;

/*
Например, может выдаваться предупреждение, что локальная переменная не используется
all - отключение всех предупреждений
boxing - отключение предупреждений, связанных с приведением к классам или простым типам
cast - отключение предупреждений, связанных с преобразованием типов
rawtypes - отключение предупреждений, связанных с использованием непараметризованных типов
null -  отключение предупреждений, связанных с анализом null
super - отключение предупреждений, связанных с переопределением метода без вызова базового метода
unchecked - отключение предупреждений, связанных с непроверенными операциями
unchecked - отключение предупреждений, связанных с неиспользуемым кодом
и т.д.
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class BuiltinDemo {
    public static void main(String[] args) {
        BuiltinDemo builtinDemo = new BuiltinDemo();
        builtinDemo.testSuppressWarning();

        DeprecatedDemo.testLegacyFunction();
    }


    public void testSuppressWarning(){
        Map map = new HashMap();
        map.put(1,"item 1");
        map.put(2,"item 2");
        map.put(3,"item 3");
    }
}

class Parent{
    public String getName(){
        return "parent";
    }
}

class Child extends Parent{
    @Override
    public String getName(){
        return "child";
    }
}

class DeprecatedDemo {

    @Deprecated(since = "4.5", forRemoval = true)
    public static void testLegacyFunction() {
        System.out.println("This is a legacy function");
    }
}

@FunctionalInterface
interface Print {
    //Не выдает предупреждение, что метод не используется
    @SuppressWarnings({"unused"})
    void printString(String testString);
    //Добавить еще один метод
}