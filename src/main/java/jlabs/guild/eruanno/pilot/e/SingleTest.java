package jlabs.guild.eruanno.pilot.e;

import java.util.Scanner;

public class SingleTest {

    private final int testNumber;

    private int n = 20000;

    private int scale = 100000;

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        int testNumber = 1;
        while (true) {
            System.out.println("About to run single test. Press enter to continue...");
            keyboard.nextLine();
            SingleTest singleTest = new SingleTest(testNumber++);
            Result result = singleTest.run();
            result.displayResult();
        }
    }

    SingleTest(int testNumber) {
        this.testNumber = testNumber;
    }

    SingleTest(int testNumber, int n, int scale) {
        this.testNumber = testNumber;
        this.n = n;
        this.scale = scale;
    }

    Result run() {
        ECalculator eCalculator = new ECalculator(n, scale, Algorithm.SEVENTH);
        eCalculator.calculateE();
        return compileResult(eCalculator);
    }

    private Result compileResult(ECalculator eCalculator) {
        String ideal = Ideal.getIdeal();
        String calculated = eCalculator.getCalculatedE().toString();
        int position = 0;
        do {
            position++;
        } while ((position < calculated.length()) && (ideal.charAt(position) == calculated.charAt(position)));
        return new Result(this.testNumber, position, eCalculator.getTimeMs(), n, scale);
    }
}
