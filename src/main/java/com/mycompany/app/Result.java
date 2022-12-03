package com.mycompany.app;

public record Result(int testNumber, int matchingDigits, long time, int n, int scale) {
    void displayResult() {
        System.out.println("Run #" + testNumber + ":");
        System.out.println("Score:\t" + matchingDigits);
        System.out.println("Time:\t" + time);
    }

    void displayTableResult() {
        System.out.println(testNumber + "\t" + n +  "\t" + scale +  "\t" +matchingDigits + "\t" + time);
    }
}
