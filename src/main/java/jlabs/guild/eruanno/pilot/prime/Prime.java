package jlabs.guild.eruanno.pilot.prime;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Integer.parseInt;
import static java.lang.System.currentTimeMillis;
import static java.util.Arrays.fill;

public class Prime {
    private static final int size = 1_000_000_000;
    private static final boolean[] sieve = new boolean[size];

    public static void main(String[] args) throws IOException {
        InputStream inputStream = Prime.class.getResourceAsStream("/prime-test");
        Scanner scanner = new Scanner(inputStream);

        System.out.println("Start");
        long start = currentTimeMillis();
        initializeSieveEratosthene();
        System.out.println("Sieve time: %d".formatted((currentTimeMillis() - start) / 1000));
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
        System.out.println("End. Time: %d".formatted((currentTimeMillis() - start) / 1000));

        scanner.close();
    }

    /**
     * 8 seconds for size = 1_000_000_000.
     */
    private static void initializeSieveEratosthene() {
        fill(sieve, true);
        for (int i = 2; i * i <= size; i++) {
            if (sieve[i]) {
                for (int temp = i * i; temp < size; temp += i) {
                    if (temp > 0) {
                        sieve[temp] = false;
                    }
                }
            }
        }
    }

    /**
     * 16 seconds for size = 1_000_000_000.
     */
    private static void initializeSieveAtkins() {
        sieve[2] = true;
        sieve[3] = true;

        for (int x = 1; x * x <= size; x++) {
            for (int y = 1; y * y <= size; y++) {
                int n = (4 * x * x) + (y * y);
                if (n <= size && (n % 12 == 1 || n % 12 == 5)) {
                    sieve[n] ^= true;
                }
                n = (3 * x * x) + (y * y);
                if (n <= size && n % 12 == 7) {
                    sieve[n] ^= true;
                }
                n = (3 * x * x) - (y * y);
                if (x > y && n <= size && n % 12 == 11) {
                    sieve[n] ^= true;
                }
            }
        }

        for (int r = 5; r * r <= size; r++) {
            if (sieve[r]) {
                for (int i = r * r; i < size; i += r * r) {
                    sieve[i] = false;
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
