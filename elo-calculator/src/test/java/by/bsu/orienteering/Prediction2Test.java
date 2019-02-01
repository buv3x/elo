package by.bsu.orienteering;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by alexey.memelov on 30-Jan-19.
 */
public class Prediction2Test {

    public static final int LIMIT = 10000000;

    public static void main(String[] args) {
        Random random = new Random();
        double total1_2 = 0;
        double total2_1 = 0;
        for (int i = 0; i < LIMIT; ++i) {
            double p = Math.random() * 0.9 + 0.05;

            double p1 = random.nextGaussian() / 50 + p;
            if(p1 > 0.975) {
                p1 = 0.975;
            }
            if(p1 < 0.025) {
                p1 = 0.025;
            }
            double p2 = random.nextGaussian() / 100 + p;
            if(p2 > 0.975) {
                p2 = 0.975;
            }
            if(p2 < 0.025) {
                p2 = 0.025;
            }

            double k1_1 = 1 / p1;
            double k1_0 = 1 / (1 - p1);

            double k2_1 = 1 / p2;
            double k2_0 = 1 / (1 - p2);

            int selected1_2;
            double selectedK1_2;
            if(p2 < p1) {
                selected1_2 = 1;
                selectedK1_2 = k2_1;
            } else {
                selected1_2 = 0;
                selectedK1_2 = k2_0;
            }

            int selected2_1;
            double selectedK2_1;
            if(p1 < p2) {
                selected2_1 = 1;
                selectedK2_1 = k1_1;
            } else {
                selected2_1 = 0;
                selectedK2_1 = k1_0;
            }

            int res = Math.random() <= p ? 1 : 0;

            if(selected2_1 == res) {
                total2_1 += (selectedK2_1 - 1);
            } else {
                total2_1 -= 1;
            }

            if(selected1_2 == res) {
                total1_2 += (selectedK1_2 - 1);
            } else {
                total1_2 -= 1;
            }
        }

        System.out.println("Total 2_1: " + new BigDecimal(total2_1 / LIMIT).setScale(5, BigDecimal.ROUND_HALF_EVEN));
        System.out.println("Total 1_2: " + new BigDecimal(total1_2 / LIMIT).setScale(5, BigDecimal.ROUND_HALF_EVEN));

    }

}
