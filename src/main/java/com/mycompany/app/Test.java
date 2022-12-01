package com.mycompany.app;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Test {

    record Result(BigDecimal r, long time){}

    public static void main(String[] args) {
        long x = 3628800;
        long start = System.currentTimeMillis();
        BigDecimal r = BigDecimal.ONE.divide(BigDecimal.valueOf(x), 1000000, RoundingMode.HALF_UP);
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        List<Result> rs = new ArrayList<>(16);
        start = System.currentTimeMillis();
        BigDecimal rn = BigDecimal.valueOf(1./x);
        for(int i = 0; i < 16; i++) {
            rn = rn.subtract((rn.multiply(BigDecimal.valueOf(x)).subtract(BigDecimal.ONE)).multiply(rn));
            rs.add(new Result(rn, System.currentTimeMillis()));
        }

        String idealR = r.toString();
        int index = 0;
        for(Result rsi : rs) {
            String calculatedR = rsi.r().toString();
            int position = 0;
            do {
                position++;
            } while ((position < calculatedR.length()) && (idealR.charAt(position) == calculatedR.charAt(position)));
            System.out.println(index++ + "\t" + position + "\t" + (rsi.time - start));
        }
    }
}