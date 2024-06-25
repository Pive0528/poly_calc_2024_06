package org.koreait;
import java.util.Arrays;
import java.util.stream.Collectors;
public class Calc {
    public static int run(String exp) {
        // 단일항이 들어오면 바로 리턴
        if (!exp.contains(" ")) {
            return Integer.parseInt(exp);
        }
        boolean needToGwal = exp.contains("(") && exp.contains(")");
        boolean needToMulti = exp.contains(" * ");
        boolean needToPlus = exp.contains(" + ") || exp.contains(" - ");

        boolean needToCompound = needToMulti && needToPlus;


        if (needToGwal) {           // 소괄호()가 있을 떄
            exp = exp.replaceAll("\\(", "").replaceAll("\\)", "");
        }

        if (needToCompound) {           // 곱하기와 더하기가 같이있는 복합 식일때
            String[] bits = exp.split(" \\+ ");
            String newExp = Arrays.stream(bits)
                    .mapToInt(Calc::run)
                    .mapToObj(e -> e + "")
                    .collect(Collectors.joining(" + "));
            return run(newExp);
        }
        if (needToPlus) {               // 더하기만 있는 식일때
            exp = exp.replaceAll("- ", "+ -");
            String[] bits = exp.split(" \\+ ");
            int sum = 0;
            for (int i = 0; i < bits.length; i++) {
                sum += Integer.parseInt(bits[i]);
            }
            return sum;
        } else if (needToMulti) {
            String[] bits = exp.split(" \\* ");
            int sum = 1;
            for (int i = 0; i < bits.length; i++) {
                sum *= Integer.parseInt(bits[i]);
            }
            return sum;
        }
        throw new RuntimeException("해석 불가 : 올바른 계산식이 아니야");
    }
}