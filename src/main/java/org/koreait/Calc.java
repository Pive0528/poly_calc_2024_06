package org.koreait;

public class Calc {
    public static int run(String exp) {
//        if (exp.equals("2 + 1")){
//            return 3;
//        }
//        else if (exp.equals("2 + 2")){
//            return 4;
//        }
        String[] bits = exp.split(" \\- ");

        int a = Integer.parseInt(bits[0]);
        int b = Integer.parseInt(bits[1]);

        return a-b;
    }
}
