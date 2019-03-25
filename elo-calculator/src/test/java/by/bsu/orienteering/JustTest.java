package by.bsu.orienteering;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by alexey.memelov on 11-Mar-19.
 */
public class JustTest {

    public static final int LIMIT = 100;

    public static void main(String[] args) {

        BigInteger a = new BigInteger("3");
        BigInteger b = new BigInteger("0");

        System.out.println("1: " + b.divide(a));

        for(int i = 2; i < LIMIT; ++i) {

            a = a.multiply(new BigInteger("3"));


            BigInteger pow = new BigInteger("2").pow(i - 1);
            BigInteger divide = pow.multiply(pow.add(new BigInteger("-1"))).divide(new BigInteger("2"));

            b = b.multiply(new BigInteger("3")).add(divide);

            System.out.println(i + ": " + b.divide(a));
        }



    }

}
