package jlabs.guild.eruanno.pilot.e;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class TestFramework {
    public static void main(String[] args) {
        TestFramework testFramework = new TestFramework();
        Scanner keyboard = new Scanner(System.in);
        int numberOfTests = 20;
        System.out.println("About to run " + numberOfTests + " tests. Press enter to continue...");
        keyboard.nextLine();
        testFramework.run(numberOfTests);
        System.out.println("Press enter to exit...");
        keyboard.nextLine();
    }

    void run(int numberOfTests) {
        System.out.println("#\tn\tscale\tscore\ttime");
        List<Result> results = IntStream.range(1, numberOfTests + 1).mapToObj(i -> new SingleTest(i, 44000, i * 10000)).map(SingleTest::run).peek(Result::displayTableResult).toList();
        System.out.println("Aggregated results of #" + results.size() + " tests:");
        System.out.println("max:\t" + results.stream().mapToInt(Result::matchingDigits).max().getAsInt());
        System.out.println("avg:\t" + results.stream().mapToInt(Result::matchingDigits).average().getAsDouble());
        System.out.println("min:\t" + results.stream().mapToInt(Result::matchingDigits).min().getAsInt());
    }
}
