package com.mycompany.app;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pattern {
    public static void main(String[] args) {
        InputStream inputStream = Pattern.class.getResourceAsStream("/pattern-test-input");
        Scanner inScanner = new Scanner(inputStream);

        int noOfImages = 0;
        if (inScanner.hasNextLine()) {
            noOfImages = Integer.parseInt(inScanner.nextLine());
        }

        for (int i = 0; i < noOfImages; i++) {
            String[] metadata = inScanner.nextLine().split(" ");
            int imageHeight = Integer.parseInt(metadata[1]);
            List<String> image = loadImage(inScanner, imageHeight);
            System.out.println(recognizeCharacter(image));
        }

        inScanner.close();
    }

    private static List<String> loadImage(Scanner inScanner, int imageHeight) {
        List<String> image = new ArrayList<>();
        for (int h = 0; h < imageHeight; h++) {
            image.add(inScanner.nextLine());
        }
        return image;
    }

    /**
     * Algorithm checks how many chunks are drawn in each quantified line from top to bottom. X has one point(group)
     * in the middle and two at top/bottom whereas O ha the opposite. If in the middle of image program finds there is
     * only one group (slopeDown) then this have to be X. Otherwise we are iterating fully and return O.
     */
    private static char recognizeCharacter(List<String> image) {
        int previousNumberOfGroups = 0;
        boolean slopeDown = false;
        boolean oSign = true;
        for (String line : image) {
            int numberOfGroups = calculateNumberOfGroups(line);
            if (numberOfGroups > 0) {
                if (previousNumberOfGroups > numberOfGroups) {
                    slopeDown = true;
                }
                if (previousNumberOfGroups < numberOfGroups && slopeDown) {
                    oSign = false;
                }
            }
            previousNumberOfGroups = numberOfGroups;
        }
        if (oSign) {
            return 'O';
        }
        return 'X';
    }

    private static int calculateNumberOfGroups(String line) {
        char previous = '.';
        int numberOfGroups = 0;
        for (int width = 0; width < line.length(); width++) {
            char character = line.charAt(width);
            if (character == '#' && previous == '.') {
                numberOfGroups++;
            }
            previous = character;
        }
        return numberOfGroups;
    }
}
