package com.mycompany.app;

import ch.obermuhlner.math.big.BigDecimalMath;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

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
        switch (algorithm) {
            case FIRST -> firstMethod();
            case SECOND -> secondMethod();
            case THIRD -> thirdMethod();
            default -> System.out.println("None algorithm was specified.");
        }
    }

    private void firstMethod() {
        this.start = System.currentTimeMillis();
        BigDecimal e = BigDecimal.ZERO;
        long now;
        for (int i = 1; i < this.n; i++) {
            e = e.add(BigDecimal.ONE.add(BigDecimal.ONE.divide(BigDecimal.valueOf(i), scale, RoundingMode.HALF_UP)).pow(n));
            if (i % 100 == 0) {
                if ((now = System.currentTimeMillis()) - start >= 3000) {
                    this.end = now;
                    break;
                }
            }
        }
        this.calculatedE = e;
    }

    private void secondMethod() {
        this.start = System.currentTimeMillis();
        BigDecimal e = BigDecimal.ZERO;
        for (int i = 0; i < this.n; i++) {
            e = e.add(BigDecimal.ONE.divide(getNextFactorial(i), scale, RoundingMode.HALF_UP));
            if (i % 100 == 0) {
                this.end = System.currentTimeMillis();
                if (end - start >= 3000) {
                    break;
                }
            }
        }
        this.calculatedE = e;
    }

    private void thirdMethod() {
        this.start = System.currentTimeMillis();
        BigDecimal e = BigDecimal.ZERO;
        MathContext mc = new MathContext(1500);
        for (int i = 0; i < this.n; i++) {
            BigDecimal factorial = getNextFactorial(i);
            BigDecimal rn = BigDecimal.ONE.divide(factorial, 1500, RoundingMode.HALF_UP);
            for(int j = 0; j < 10; j++) {
                rn = rn.subtract((rn.multiply(factorial).subtract(BigDecimal.ONE, mc)).multiply(rn), mc);
            }
            e = e.add(rn);
            if (i % 100 == 0) {
                this.end = System.currentTimeMillis();
                if (end - start >= 3000) {
                    System.out.println(i);
                    break;
                }
            }
        }
        this.calculatedE = e;
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
}
