package org.itstep.annotations.builtin;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked"})
public class BuiltinDemo {
    public static void main(String[] args) {
        BuiltinDemo builtinDemo = new BuiltinDemo();
        builtinDemo.testSupressWarning();

        DeprecatedDemo.testLegacyFunction();
    }


    public void testSupressWarning(){
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
    //public String getname(){
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
    void printString(String testString);
    //Добавить еще один метод
}