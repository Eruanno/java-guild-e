package jlabs.guild.eruanno.pilot.e;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class App {

    // Number of iterations.
    private static final int n = 44000;

    // How many decimal places we are calculating.
    private static final int accuracy = 150000;

    public static void main(String[] args) {
        System.out.println(calculate());
    }

    /**
     * Algorithm found here: https://www.nayuki.io/page/approximating-eulers-number-correctly optimized for the limit of time terminator.
     */
    private static String calculate() {
        long start = System.currentTimeMillis();
        int extraPrecision = 7;
        final BigDecimal fullScaler = BigDecimal.TEN.pow(accuracy + extraPrecision);
        final BigDecimal extraScaler = BigDecimal.TEN.pow(extraPrecision);
        BigDecimal sumLow = fullScaler;
        BigDecimal termLow = fullScaler.divide(BigDecimal.ONE, 0, RoundingMode.HALF_EVEN);
        BigDecimal temp = BigDecimal.ONE;
        for (int i = 1; i < n; i++) {
            sumLow = sumLow.add(termLow);
            if (i % 1000 == 0) {
                temp = sumLow.divide(extraScaler, 0, RoundingMode.HALF_EVEN);
                if (System.currentTimeMillis() - start >= 3000) {
                    break;
                }
            }
            BigDecimal j = BigDecimal.valueOf(i + 1);
            termLow = termLow.divide(j, 0, RoundingMode.HALF_EVEN);
        }
        return "2." + temp.toString().substring(1);
    }
}
