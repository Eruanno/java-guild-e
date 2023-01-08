package jlabs.guild.eruanno.pilot.e;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;

public class ECalculator {
    private final int n;

    private final int scale;

    private final Algorithm algorithm;

    private BigDecimal calculatedE = BigDecimal.ZERO;

    private BigDecimal currentFactorial = BigDecimal.ONE;

    private long start = 0;

    private long end = 0;

    public static void main(String[] args) {
        ECalculator eCalculator = new ECalculator(10000, 19500, Algorithm.SECOND);
        eCalculator.calculateE();
        System.out.println(eCalculator.getCalculatedE());
    }

    ECalculator(int n, int scale, Algorithm algorithm) {
        this.n = n;
        this.scale = scale;
        this.algorithm = algorithm;
    }


    BigDecimal getCalculatedE() {
        return this.calculatedE;
    }

    long getTimeMs() {
        return end - start;
    }

    void calculateE() {
        this.start = System.currentTimeMillis();
        switch (algorithm) {
            case FIRST -> firstMethod();
            case SECOND -> secondMethod();
            case THIRD -> thirdMethod();
            case FOURTH -> fourthMethod();
            case FIFTH -> fifthMethod();
            case SIXTH -> sixthMethod();
            case SEVENTH -> seventhMethod();
            default -> System.out.println("None algorithm was specified.");
        }
    }

    /**
     * Naive approach. (1 + 1/i)^n
     */
    private void firstMethod() {
        BigDecimal e = BigDecimal.ZERO;
        for (int i = 1; i < this.n; i++) {
            e = e.add(BigDecimal.ONE.add(BigDecimal.ONE.divide(BigDecimal.valueOf(i), scale, RoundingMode.HALF_UP)).pow(n));
            if (stop(i)) {
                break;
            }
        }
        this.calculatedE = e;
    }

    /**
     * Sum of 1/i!.
     */
    private void secondMethod() {
        BigDecimal e = BigDecimal.ZERO;
        for (int i = 0; i < this.n; i++) {
            e = e.add(BigDecimal.ONE.divide(getNextFactorial(i), scale, RoundingMode.HALF_UP));
            if (stop(i)) {
                break;
            }
        }
        this.calculatedE = e;
    }

    /**
     * Same as second method but division is approccimated.
     * http://www.numberworld.org/y-cruncher/internals/division.html
     */
    private void thirdMethod() {
        BigDecimal e = BigDecimal.ZERO;
        MathContext mc = new MathContext(scale);
        for (int i = 0; i < this.n; i++) {
            BigDecimal factorial = getNextFactorial(i);
            BigDecimal rn = BigDecimal.ONE.divide(factorial, scale, RoundingMode.HALF_UP);
            for (int j = 0; j < 10; j++) {
                rn = rn.subtract((rn.multiply(factorial).subtract(BigDecimal.ONE, mc)).multiply(rn), mc);
            }
            e = e.add(rn);
            if (stop(i)) {
                break;
            }
        }
        this.calculatedE = e;
    }

    /**
     * Serial Horner. Good for multithread.
     * https://stackoverflow.com/questions/62499691/how-to-calculate-eulers-number-faster-with-java-multithreading
     */
    private void fourthMethod() {
        BigDecimal e = BigDecimal.ONE;
        for (int i = this.n; i > 0; i--) {
            e = e.divide(BigDecimal.valueOf(i), scale, RoundingMode.HALF_EVEN).add(BigDecimal.ONE);
        }
        this.end = System.currentTimeMillis();
        this.calculatedE = e;
    }

    /**
     * Not working.
     * https://gist.github.com/anonymous/4047379
     */
    private void fifthMethod() {
        BigDecimal divisor = BigDecimal.ZERO;
        for (int i = 0; i < this.n; i++) {
            divisor = divisor.add(getNextFactorial(i));
        }
        BigDecimal e = BigDecimal.valueOf(this.n).divide(divisor, scale, RoundingMode.HALF_EVEN);
        this.end = System.currentTimeMillis();
        this.calculatedE = e;
    }

    /**
     * E spigot algorithm
     */
    private void sixthMethod() {
        int[] array = new int[n];
        Arrays.fill(array, 1);
        StringBuilder e = new StringBuilder("2.");
        for (int i = 0; i < n; i++) {
            int carry = 0;
            for (int j = n - 1; j >= 2; j--) {
                int temp = array[j] * 10 + carry;
                carry = temp / j;
                array[j] = temp - carry * j;
            }
            e.append(carry);
            if (stop(i)) {
                break;
            }
        }
        this.end = System.currentTimeMillis();
        this.calculatedE = new BigDecimal(e.toString());
    }

    /**
     * https://www.nayuki.io/page/approximating-eulers-number-correctly
     */
    private void seventhMethod() {
        int extraPrecision = 7;
        final BigDecimal fullScaler = BigDecimal.TEN.pow(this.scale + extraPrecision);
        final BigDecimal extraScaler = BigDecimal.TEN.pow(extraPrecision);
        BigDecimal sumLow = fullScaler;
        BigDecimal termLow = fullScaler.divide(BigDecimal.ONE, 0, RoundingMode.HALF_EVEN);
        BigDecimal temp = BigDecimal.ONE;
        for (int i = 1; i < this.n; i++) {
            sumLow = sumLow.add(termLow);
            if (i % 1000 == 0) {
                temp = sumLow.divide(extraScaler, 0, RoundingMode.HALF_EVEN);
                this.end = System.currentTimeMillis();
                if (end - start >= 2000) {
                    break;
                }
            }
            termLow = termLow.divide(BigDecimal.valueOf(i + 1), 0, RoundingMode.HALF_EVEN);
        }
        this.calculatedE = new BigDecimal("2." + temp.toString().substring(1));
    }

    private BigDecimal getNextFactorial(long n) {
        if (n == 0) {
            return BigDecimal.ONE;
        }
        if (n == 1) {
            return BigDecimal.ONE;
        }
        return (currentFactorial = currentFactorial.multiply(BigDecimal.valueOf(n)));
    }

    private boolean stop(int i) {
        if (i % 1000 == 0) {
            this.end = System.currentTimeMillis();
            return end - start >= 3000;
        }
        return false;
    }
}
