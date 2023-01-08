package jlabs.guild.eruanno.pilot.e;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Euler {

    public static void main(String[] args) throws FileNotFoundException {
        long start = System.currentTimeMillis();

        long[] in = new long[11900];
        long[] out = new long[11900];

        in[0] = 1;
        out[0] = 1;
        long shifter = 100_000_000_000_000L;

        int divisor = 2;
        while (System.currentTimeMillis() - start < 2970) {
            div(in, divisor++, shifter);
            add(out, in);
        }

        PrintWriter pw = new PrintWriter("out.txt");
        print(fix(out, shifter), pw);
        pw.close();

        long end = System.currentTimeMillis();
        System.out.println("\ntime: " + (end - start));

        String data = read("out.txt");
        String model = read("e.txt");

        int index = 2;
        while (data.charAt(index) == model.charAt(index)) {
            index++;
        }
        System.out.println("\nscore: " + (index - 2));
    }

    private static void div(long[] in, long divisor, long shifter) {
        long count = in[0];
        for (int i = 0; i < in.length; i++) {
            if (count > 0) {
                in[i] = count / divisor;
                count -= in[i] * divisor;
            }

            if (i + 1 < in.length) {
                count *= shifter;
                count += in[i + 1];
            }
        }
    }

    private static void add(long[] val, long[] param) {
        for (int i = 0; i < val.length; i++) {
            val[i] += param[i];
        }
    }

    private static void print(long[] val, PrintWriter os) {
        os.print("2.");
        for (int i = 1; i < val.length; i++) {
            if (val[i] < 10_000_000_000_000L) {
                os.printf("%014d", val[i]);
            } else {
                os.print(val[i]);
            }
        }
    }

    private static String read(String file) throws FileNotFoundException {
        return new Scanner(new File(file)).next();
    }

    private static long[] fix(long[] val, long shifter) {
        for (int i = val.length - 1; i > 0; i--) {
            if (val[i] > shifter) {
                val[i - 1] += val[i] / shifter;
                val[i] = val[i] % shifter;
            }
        }
        return val;
    }
}
