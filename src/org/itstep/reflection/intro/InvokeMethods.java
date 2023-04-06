package org.itstep.reflection.intro;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InvokeMethods {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //Раскомментировать в конце
        //System.setSecurityManager(new SecurityManager());

        //Вызов метода sum
        //getMethod - только для методов public
        Method sum = Calc.class.getMethod("sum", int.class, double.class);
        Calc calc = new Calc();
        double resSum = (Double) sum.invoke(calc, 1, 3);
        System.out.println(resSum);

        //Аналогично метод mult
        //Вызов статического метода не требует создания объекта
        Method mult = Calc.class.getMethod("mult", float.class, long.class);
        double resMult = (Double) mult.invoke(null, 2, 3);
        System.out.println(resMult);

        //Метод с модификатором private так не вызвать: NoSuchMethodException
        //getDeclaredMethod - для методов public, protected, default, private, но не для унаследованных методов
        //Надо использовать getDeclaredMethod, теперь IllegalAccessException
        //Изменим доступ с помощью setAccessible
        Method and = Calc.class.getDeclaredMethod("and", boolean.class, boolean.class);
        and.setAccessible(true);
        boolean resAnd = (Boolean) and.invoke(calc, true, true);
        System.out.println(resAnd);

        //Метод protected можно вызвать и без изменения доступа
        Method max = Calc.class.getDeclaredMethod("max", int.class, int.class);
        //max.setAccessible(true);
        int resMax = (Integer) max.invoke(calc, 2, 1);
        System.out.println(resMax);
    }
}

//Класс, в котором содержатся методы с различным доступом и аргументами
class Calc{

    public double sum(int a, double b) {
        return a + b;
    }

    public static double mult(float a, long b) {
        return a * b;
    }

    private boolean and(boolean a, boolean b) {
        return a && b;
    }

    protected int max(int a, int b) {
        return a > b ? a : b;
    }
}