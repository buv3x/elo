package by.bsu.orienteering;

import java.math.BigDecimal;

/**
 * Created by alexey.memelov on 14-Jan-19.
 */
public class PredictionTest {

    public static final int LIMIT = 10000000;

    public static void main(String[] args) {

        double total3_1 = 0;
        double total3_2 = 0;
        double total2_1 = 0;
        double total2_3 = 0;
        double total1_3 = 0;
        double total1_2 = 0;

        for (int i = 0; i < LIMIT; ++i) {
            double rand1 = Math.random();
            double rand2 = Math.random();


            double exp1 = 0.5;
            double k1_1 = 1 / exp1;
            double k1_0 = 1 / (1 - exp1);
            double exp2 = (rand1 + 0.5) / 2;
            double k2_1 = 1 / exp2;
            double k2_0 = 1 / (1 - exp2);
            double exp3 = (rand1 + rand2) / 2;
            double k3_1 = 1 / exp3;
            double k3_0 = 1 / (1 - exp3);

            int selected3_1;
            double selectedK3_1;
            if(exp1 < exp3) {
                selected3_1 = 1;
                selectedK3_1 = k1_1;
            } else {
                selected3_1 = 0;
                selectedK3_1 = k1_0;
            }

            int selected3_2;
            double selectedK3_2;
            if(exp2 < exp3) {
                selected3_2 = 1;
                selectedK3_2 = k2_1;
            } else {
                selected3_2 = 0;
                selectedK3_2 = k2_0;
            }

            int selected2_1;
            double selectedK2_1;
            if(exp1 < exp2) {
                selected2_1 = 1;
                selectedK2_1 = k1_1;
            } else {
                selected2_1 = 0;
                selectedK2_1 = k1_0;
            }

            int selected2_3;
            double selectedK2_3;
            if(exp3 < exp2) {
                selected2_3 = 1;
                selectedK2_3 = k3_1;
            } else {
                selected2_3 = 0;
                selectedK2_3 = k3_0;
            }

            int selected1_2;
            double selectedK1_2;
            if(exp2 < exp1) {
                selected1_2 = 1;
                selectedK1_2 = k2_1;
            } else {
                selected1_2 = 0;
                selectedK1_2 = k2_0;
            }

            int selected1_3;
            double selectedK1_3;
            if(exp3 < exp1) {
                selected1_3 = 1;
                selectedK1_3 = k3_1;
            } else {
                selected1_3 = 0;
                selectedK1_3 = k3_0;
            }

            int res = Math.random() <= exp3 ? 1 : 0;

            if(selected3_1 == res) {
                total3_1 += (selectedK3_1 - 1);
            } else {
                total3_1 -= 1;
            }

            if(selected3_2 == res) {
                total3_2 += (selectedK3_2 - 1);
            } else {
                total3_2 -= 1;
            }

            if(selected2_1 == res) {
                total2_1 += (selectedK2_1 - 1);
            } else {
                total2_1 -= 1;
            }

            if(selected2_3 == res) {
                total2_3 += (selectedK2_3 - 1);
            } else {
                total2_3 -= 1;
            }

            if(selected1_2 == res) {
                total1_2 += (selectedK1_2 - 1);
            } else {
                total1_2 -= 1;
            }

            if(selected1_3 == res) {
                total1_3 += (selectedK1_3 - 1);
            } else {
                total1_3 -= 1;
            }

//            System.out.println("(" + rand1 + " : " + rand2 + ") " + k1_1 + " : " + k2_1 + " : " + k3_1 + " : " + res);

        }

        System.out.println("Total 3_1: " + new BigDecimal(total3_1 / LIMIT).setScale(5, BigDecimal.ROUND_HALF_EVEN));
        System.out.println("Total 3_2: " + new BigDecimal(total3_2 / LIMIT).setScale(5, BigDecimal.ROUND_HALF_EVEN));
        System.out.println("Total 2_1: " + new BigDecimal(total2_1 / LIMIT).setScale(5, BigDecimal.ROUND_HALF_EVEN));
        System.out.println("Total 2_3: " + new BigDecimal(total2_3 / LIMIT).setScale(5, BigDecimal.ROUND_HALF_EVEN));
        System.out.println("Total 1_2: " + new BigDecimal(total1_2 / LIMIT).setScale(5, BigDecimal.ROUND_HALF_EVEN));
        System.out.println("Total 1_3: " + new BigDecimal(total1_3 / LIMIT).setScale(5, BigDecimal.ROUND_HALF_EVEN));


    }

}
