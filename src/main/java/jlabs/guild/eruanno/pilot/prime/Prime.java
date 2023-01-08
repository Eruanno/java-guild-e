package jlabs.guild.eruanno.pilot.prime;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Integer.parseInt;
import static java.lang.Math.sqrt;

public class Prime {
    private static final int size = 1_000_000_000;
    private static final boolean[] sieve = new boolean[size];

    public static void main(String[] args) throws IOException {
        InputStream inputStream = Prime.class.getResourceAsStream("/prime-test-long");
        Scanner scanner = new Scanner(inputStream);

        System.out.println("Start");
        initializeSieve();
        long start = System.currentTimeMillis();
        int exampleCount = parseInt(scanner.nextLine());
        for (int i = 0; i < exampleCount; i++) {
            String line = scanner.nextLine();

            int m = parseInt(line.split(" ")[0]);
            int n = parseInt(line.split(" ")[1]);

            for (int number = m; number <= n; number++) {
                if (sieve[number]) {
                    //System.out.println(number);
                }
            }
            //System.out.println("%d %f%% %ds".formatted(i, (i / (float) exampleCount * 100f), (System.currentTimeMillis() - start) / 1000));
            //System.out.println();
        }
        System.out.println("End. Time: %d".formatted((System.currentTimeMillis() - start) / 1000));

        scanner.close();
    }

    private static void initializeSieve() {
        Arrays.fill(sieve, true);
        for (int i = 2; i <= sqrt(size); i++) {
            if (sieve[i]) {
                for (int temp = i * i; temp < size; temp += i) {
                    if (temp > 0) {
                        sieve[temp] = false;
                    }
                }
            }
        }
    }

    private static void generateExample() throws IOException {
        FileOutputStream outputStream = new FileOutputStream("prime-test-long");
        outputStream.write("1000000\n".getBytes());
        for (int i = 0; i < 1_000_000; i++) {
            int m = ThreadLocalRandom.current().nextInt(0, size + 1 - 999_999);
            int n = m + 999_999;
            String line = m + " " + n + "\n";
            outputStream.write(line.getBytes());
        }
        outputStream.close();
    }
}
