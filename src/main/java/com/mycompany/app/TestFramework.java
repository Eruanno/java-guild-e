package com.mycompany.app;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class TestFramework {
    public static void main(String[] args) {
        TestFramework testFramework = new TestFramework();
        Scanner keyboard = new Scanner(System.in);
        System.out.println("About to run 10 tests. Press enter to continue...");
        keyboard.nextLine();
        testFramework.run(10);
        System.out.println("Press enter to exit...");
        keyboard.nextLine();
    }

    void run(int numberOfTests) {
        List<Result> results = IntStream.range(1, numberOfTests + 1).mapToObj(SingleTest::new).map(SingleTest::run).peek(Result::displayResult).toList();
        System.out.println("Aggregated results of #" + results.size() + " tests:");
        System.out.println("max:\t" + results.stream().mapToInt(Result::matchingDigits).max().getAsInt());
        System.out.println("avg:\t" + results.stream().mapToInt(Result::matchingDigits).average().getAsDouble());
        System.out.println("min:\t" + results.stream().mapToInt(Result::matchingDigits).min().getAsInt());
    }
}
