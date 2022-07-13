package org.itstep.annotations.test;

import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeMap;

//Вызвать в определенном порядке все методы класса, помеченные @Order
public class TestDemo {
    public static void main(String[] args) {
        Test test = new Test();
        TestProcessor testProcessor = new TestProcessor(test);
        testProcessor.run();
    }
}

class Test{
    @Order(3)
    public void run1(){
        System.out.println("run 1");
    }

    @Order(2)
    public void run2(){
        System.out.println("run 2");
    }

    @Order(1)
    public void run3(){
        System.out.println("run 3");
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) // Используется только для class, enum, interface
@interface Order{
    public int value();
}

class TestProcessor {
    private Test test;

    public TestProcessor(Test test) {
        this.test = test;
    }

    public void run(){
        Method[] methods = test.getClass().getDeclaredMethods();
        Map<Integer, Method> map = new TreeMap<>();
        for (Method method: methods) {
            Annotation annotation = method.getAnnotation(Order.class);
            if (annotation instanceof Order){
                map.put(((Order) annotation).value(), method);
            }
        }
        for (Map.Entry<Integer, Method> entry : map.entrySet()) {
            try {
                entry.getValue().invoke(test);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}