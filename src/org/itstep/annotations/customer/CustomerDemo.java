package org.itstep.annotations.customer;

import java.lang.annotation.*;

public class CustomerDemo {
    public static void main(String[] args) {
        EmployeeProcessor.process();
        RepeatedAnnotatedEmployeeProcessor.process();
    }
}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited //Мета-аннотация. В дочерних классах также сработает, проверить при закомментированной аннотации
@interface Company{
    String name() default "Step";
    String city() default "Vitebsk";
}

//Присоединить ко всем сотрудникам информацию о фирме
@Company
class Employee {
    private int id;
    private String name;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void getDetails(){
        System.out.println("Employee Id: " + id);
        System.out.println("Employee Name: " + name);
    }
}

class Manager extends Employee{
    public Manager(int id, String name) {
        super(id, name);
    }
}

class EmployeeProcessor{
    public static void process(){
        //Employee employee = new Employee(1, "John Doe");
        Employee employee = new Manager(1, "John Doe");
        employee.getDetails();

        Annotation companyAnnotation = employee
                .getClass()
                .getAnnotation(Company.class);
        Company company = (Company)companyAnnotation;

        System.out.println("Company Name: " + company.name());
        System.out.println("Company City: " + company.city());
    }
}

@Target(ElementType.TYPE)
@Repeatable(RepeatableCompanies.class)
@Retention(RetentionPolicy.RUNTIME)
@interface RepeatableCompany {
    String name() default "Name_1";
    String city() default "City_1";
}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface RepeatableCompanies {
    RepeatableCompany[] value() default{};
}

@RepeatableCompany
@RepeatableCompany(name =  "Name_2", city = "City_2")
class RepeatedAnnotatedEmployee extends Employee{
    public RepeatedAnnotatedEmployee(int id, String name) {
        super(id, name);
    }
}

class RepeatedAnnotatedEmployeeProcessor{
    public static void process(){
        Employee employee = new RepeatedAnnotatedEmployee(1, "John Doe");
        employee.getDetails();
/*
        Annotation companyAnnotation = employee
                .getClass()
                .getAnnotation(RepeatableCompany.class);

 */
        RepeatableCompany[] repeatableCompanies = RepeatedAnnotatedEmployee.class
                .getAnnotationsByType(RepeatableCompany.class);
        for (RepeatableCompany repeatableCompany : repeatableCompanies) {
            System.out.println("Name: " + repeatableCompany.name());
            System.out.println("City: " + repeatableCompany.city());
        }
    }
}