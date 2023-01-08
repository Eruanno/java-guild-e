package jlabs.guild.eruanno.pilot.e;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class TestDivision {
    private BigDecimal decimalFactorial = BigDecimal.ONE;
    private BigInteger integerFactorial = BigInteger.ONE;

    public static void main(String[] args) {
        new TestDivision().test();
    }

    void test() {
        // BigDecimal
        long start = System.currentTimeMillis();
        String decimalResult = "";
        for (int i = 1; i < 1000; i++) {
            decimalResult = BigDecimal.ONE.divide(getNextDecimalFactorial(i), 19500, RoundingMode.HALF_UP).toString();
        }
        long end = System.currentTimeMillis();
        System.out.println(decimalResult);
        System.out.println("BigDecimal: " + (end - start));

        // BigInteger
        start = System.currentTimeMillis();
        String integerResult = "";
        for (int i = 1; i < 1000; i++) {
            integerResult = divideAndRound(BigInteger.ONE,getNextItegerFactorial(i)).toString();
        }
        end = System.currentTimeMillis();
        System.out.println(integerResult);
        System.out.println("BigInteger: " + (end - start));

        // Summary
        int position = 0;
        do {
            position++;
        } while ((position < integerResult.length()) && (decimalResult.charAt(position) == integerResult.charAt(position)));
        System.out.println("Accuracy:" + position);

        /**
         * For i = 1 000 000
         * BigDecimal: 277531
         * BigInteger: 282781
         */
    }

    private BigInteger divideAndRound(BigInteger num, BigInteger div) {
        BigInteger[] quotRem = num.divideAndRemainder(div);
        BigInteger result = quotRem[0];
        int cmp = quotRem[1].shiftLeft(1).compareTo(div);
        if (cmp > 0 || cmp == 0 && result.testBit(0))
            result = result.add(BigInteger.ONE);
        return result;
    }

    private BigDecimal getNextDecimalFactorial(long n) {
        if (n == 0) {
            return BigDecimal.ONE;
        }
        if (n == 1) {
            return BigDecimal.ONE;
        }
        return (decimalFactorial = decimalFactorial.multiply(BigDecimal.valueOf(n)));
    }

    private BigInteger getNextItegerFactorial(long n) {
        if (n == 0) {
            return BigInteger.ONE;
        }
        if (n == 1) {
            return BigInteger.ONE;
        }
        return (integerFactorial = integerFactorial.multiply(BigInteger.valueOf(n)));
    }
}