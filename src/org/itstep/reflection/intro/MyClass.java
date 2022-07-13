package org.itstep.reflection.intro;

public class MyClass {
    private int number;
    private String name = "default";
    private int value;

    //Закомментировать
    public MyClass(int number, String name, int value) {
        this.number = number;
        this.name = name;
        this.value = value;
    }

    public MyClass(){} //Добавить позже

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void printData() {
        System.out.println("number=" + number + ", name=" + name+ ", value="+ value);
    }
}
