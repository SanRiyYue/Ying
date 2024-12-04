package org.example;

import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
        String[] arrays = new String[] {"apple", "Orange", "banana", "Lemon"};
        Arrays.sort(arrays, String::compareTo) ;
    }

    static int cmp(String a, String b) {
        return a.compareTo(b);
    }
}