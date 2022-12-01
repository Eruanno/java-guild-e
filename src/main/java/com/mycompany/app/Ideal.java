package com.mycompany.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

public class Ideal {
    private static Optional<String> ideal;

    static {
        ideal = Optional.empty();
    }

    private Ideal() {
    }

    static String getIdeal() {
        if (ideal.isEmpty()) {
            try {
                readFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return ideal.get();
    }

    private static void readFile() throws IOException {
        Class clazz = SingleTest.class;
        InputStream inputStream = clazz.getResourceAsStream("/ideal");
        ideal = Optional.of(readFromInputStream(inputStream));
    }

    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
