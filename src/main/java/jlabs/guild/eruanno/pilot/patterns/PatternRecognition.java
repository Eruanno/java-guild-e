package jlabs.guild.eruanno.pilot.patterns;

import java.util.Scanner;


public class PatternRecognition {

    public static void main(String[] args) {
        Scanner inScanner = new Scanner(System.in);

        int noExamples = 0;
        if (inScanner.hasNextLine()) {
            noExamples = Integer.parseInt(inScanner.nextLine());
        }

        for (int i = 0; i < noExamples; i++) {
            int width = 0;
            int height = 0;
            if (inScanner.hasNextLine()) {
                String[] params = inScanner.nextLine().split(" ");
                height = Integer.parseInt(params[0]);
                width = Integer.parseInt(params[1]);
            }

            char[][] data = new char[height][width];
            for (int h = 0; h < height; h++) {
                String pixels = inScanner.nextLine();

                for (int w = 0; w < width; w++) {
                    data[h][w] = pixels.charAt(w);
                }
            }

            if (isACircle(data)) {
                System.out.println("O");
            } else {
                System.out.println("X");
            }
        }

        inScanner.close();
    }


    /**
     * Metoda sprawdza, czy nad/pod/na lewo/na prawo od punktu (w dowolnej odległości)
     * <p>
     * znajduje się '#', jeżel tak, to znaczy, że punkt jest otoczony '#' z czterech stron
     * <p>
     * i jest spora szansa, że na obrazie jest kółko
     * <p>
     * x
     * <p>
     * x.x
     * <p>
     * x
     *
     * @param data
     * @param pi
     * @param pj
     * @return
     */

    private static boolean hasIt(char[][] data, int pi, int pj) {
        boolean hasUp = false;
        for (int i = pi; i >= 0; i--) {
            hasUp |= data[i][pj] == '#';
        }
        boolean hasDown = false;
        for (int i = pi; i < data.length; i++) {
            hasDown |= data[i][pj] == '#';
        }
        boolean hasLeft = false;
        for (int j = pj; j >= 0; j--) {
            hasLeft |= data[pi][j] == '#';
        }
        boolean hasRight = false;
        for (int j = pj; j < data[pi].length; j++) {
            hasRight |= data[pi][j] == '#';
        }
        int idx = 0;
        idx += hasUp ? 1 : 0;
        idx += hasDown ? 1 : 0;
        idx += hasLeft ? 1 : 0;
        idx += hasRight ? 1 : 0;
        return idx > 3 && data[pi][pj] == '.';
    }

    private static boolean isACircle(char[][] data) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (hasIt(data, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }
}
