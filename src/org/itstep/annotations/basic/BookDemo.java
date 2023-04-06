package org.itstep.annotations.basic;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.Arrays;

public class BookDemo {
    public static void main(String[] args) {
        Book book = new Book("Title", "author", 2000, 1);
        BookProcessor bookProcessor = new BookProcessor(book);
        bookProcessor.print();

        book.title = null;
        bookProcessor.checkFieldsNotNull();

        book.pages = 0;
        bookProcessor.checkFieldsPositive();
    }
}

//Определение: Аннотация - краткое изложение книги, статьи и т.д.
//Сама по себе аннотация ничего не делает, нужно 3 компонента: класс, примененная к нему аннотация, обработчик аннотаций
//Класс "книга" помечен аннотацией, значение которой влияет на формат выводимой о книге информации
@BookAnnotation("short") //Аннотация определяет формат вывода в консоль данных о книге
class Book{
    @NonNull
    public String title;
    public String author;
    public int year;
    @IntPositive
    public int pages;

    public Book(String title, String author, int year, int pages) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.pages = pages;
    }

    public void printShort(){
        System.out.println("Title: "+title+", author:"+author);
    }

    public void printLong(){
        System.out.println("Title: "+title+", author:"+author+ ", year"+year);
    }
}

/*
Retention - удержание; мета-аннотация, которая может быть применена к другим аннотациям
Имеет 3 политики: SOURCE, CLASS, RUNTIME, которые показывают, когда аннотация применяется (и отбрасывается)
Политику SOURCE имеют такие аннотации, как @SupressWarnings, @Override
@Target - type, field, method, constructor, package, parameter
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) // Используется только для class, enum, interface
@interface BookAnnotation{
    public String value();
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NonNull{}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface IntPositive{}

class BookProcessor{
    private Book book;

    public BookProcessor(Book book){
        this.book = book;
    }

    public void print(){
        Class clazz = Book.class;

        //Получить информацию об определенной аннотации
        Annotation bookAnnotation = clazz.getAnnotation(BookAnnotation.class);
        System.out.println(bookAnnotation);

        //Получить информацию о всех аннотациях
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof BookAnnotation){
                if (((BookAnnotation) annotation).value().equals("short"))
                    book.printShort();
                if (((BookAnnotation) annotation).value().equals("long"))
                    book.printLong();
            }
        }
    }

    //Проверка того, что поле не является null
    public void checkFieldsNotNull(){
        Field[] fields = book.getClass().getDeclaredFields();
        //System.out.println(Arrays.toString(fields));
        for (Field field:fields){
            Annotation annotation = field.getAnnotation(NonNull.class);
            try {
                if (annotation!=null && field.get(book)==null)
                    System.err.println("Field '"+field.getName()+"' cannot be null");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    //Проверка того, что поле является положительным целым числом
    public void checkFieldsPositive(){
        Field[] fields = book.getClass().getDeclaredFields();
        for (Field field:fields){
            Annotation annotation = field.getAnnotation(IntPositive.class);
            try {
                if (annotation!=null && field.getInt(book)<=0)
                    System.err.println("Field '"+field.getName()+"' must be positive integer");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}