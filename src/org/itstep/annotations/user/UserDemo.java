package org.itstep.annotations.user;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class UserDemo {
    public static void main(String[] args) {
        User user = new User();
        InitConstructorProcess.init(user);
        DoSomeThingProcess.init(user);
        System.out.println(user.toString());
    }
}

class User {
    public String name;
    public int age;

    @InitConstructor(name="alex", age=49)
    public User() {
    }

    @DoSomeThing(action = "working", duration = "60m")
    public void doSomeThing() {
        System.out.println("resting");
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.CONSTRUCTOR)
@interface InitConstructor {
    public String name() default "user";
    public int age() default 30;
}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface DoSomeThing {
    public String action();
    public String duration();
}

class InitConstructorProcess {

    public static void init(Object object){
        if (object instanceof User) {
            Class clazz = object.getClass();
            Constructor[] constructors = clazz.getConstructors();
            for (Constructor constructor : constructors) {
                if (constructor.isAnnotationPresent(InitConstructor.class)) {
                    InitConstructor initConstructor = (InitConstructor) constructor.getAnnotation(InitConstructor.class);
                    ((User) object).name = initConstructor.name();
                    ((User) object).age = initConstructor.age();
                }
            }
        }else{
            throw new RuntimeException("The type of object doesn't match User类");
        }
    }
}

class DoSomeThingProcess {
    public static void init(Object object) {
        if (object instanceof User) {
            Class clazz = object.getClass();
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(DoSomeThing.class)) {
                    if(Modifier.isPrivate(method.getModifiers())){
                        method.setAccessible(true);
                    }else {
                        DoSomeThing doSomeThing =  method.getAnnotation(DoSomeThing.class);
                        System.out.println(doSomeThing.action() + "时间为 " + doSomeThing.duration());
                        try {
                            method.invoke(object);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }else {
            throw new RuntimeException("Runtime exception类");
        }
    }
}