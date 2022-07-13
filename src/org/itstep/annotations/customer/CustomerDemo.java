package org.itstep.annotations.customer;

import java.lang.annotation.*;

public class CustomerDemo {
    public static void main(String[] args) {
        EmployeeProcessor.process();
    }
}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface Company{
    String name() default "Step";
    String city() default "Vitebsk";
}

@Company
class Employee {

    private int id;
    private String name;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void getEmployeeDetails(){
        System.out.println("Employee Id: " + id);
        System.out.println("Employee Name: " + name);
    }
}

class EmployeeProcessor{
    public static void process(){
        Employee employee = new Employee(1, "John Doe");
        employee.getEmployeeDetails();

        Annotation companyAnnotation = employee
                .getClass()
                .getAnnotation(Company.class);
        Company company = (Company)companyAnnotation;

        System.out.println("Company Name: " + company.name());
        System.out.println("Company City: " + company.city());
    }
}