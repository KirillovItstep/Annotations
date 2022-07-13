package org.itstep.annotations.builtin;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class SafeVarargsTest {

    private void printString(String test1, String test2) {
        System.out.println(test1);
        System.out.println(test2);
    }

    private void printStrings(@NotNull String... tests){
        for (String test : tests) {
            System.out.println(test);
        }
    }

    private void printStringVarargs(@NotNull String... tests) {
        for (String test : tests) {
            System.out.println(test);
        }
    }

    //Possible heap pollution
    private void printStringSafeVarargs(@NotNull List<String>... testStringLists) {
        for (List<String> testStringList : testStringLists) {
            for (String testString : testStringList) {
                System.out.println(testString);
            }
        }
    }

    public static void main(String[] args) {
        SafeVarargsTest test = new SafeVarargsTest();

        test.printString("String1", "String2");
        test.printStrings("*******");

        test.printStringVarargs("String1", "String2");
        test.printStrings("*******");

        List<String> testStringList1 = Arrays.asList("One", "Two");
        List<String> testStringList2 = Arrays.asList("Three", "Four");

        test.printStringSafeVarargs(testStringList1, testStringList2);
    }
}
