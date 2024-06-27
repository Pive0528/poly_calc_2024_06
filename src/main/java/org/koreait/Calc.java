package org.koreait;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Calc {

    // 디버그 모드 플래그
    public static boolean debug = true;
    // run 메서드 호출 횟수
    public static int runCallCount = 0;

    public static int run(String exp) {
        // run 메서드 호출 횟수 증가
        runCallCount++;

        // 양 옆의 쓸데없는 공백 제거
        exp = exp.trim();
        // 괄호 제거
        exp = stripOuterBrackets(exp);

        // 만약에 -( 패턴이라면, 내가 갖고있는 코드는 해석할 수 없으므로 해석할 수 있는 형태로 수정
        if (isCaseMinusBracket(exp)) {
            exp = exp.substring(1) + " * -1";
        }
        // 디버그 모드인 경우 표시
        if (debug) {
            System.out.printf("exp(%d) : %s\n", runCallCount, exp);
        }

        // 단일항이 들어오면 바로 리턴
        if (!exp.contains(" ")) {
            return Integer.parseInt(exp);
        }

        // 곱셈, 덧셈, 괄호가 있는지 여부 확인
        boolean needToMulti = exp.contains(" * ");
        boolean needToPlus = exp.contains(" + ") || exp.contains(" - ");
        boolean needToSplit = exp.contains("(") || exp.contains(")");
        boolean needToCompound = needToMulti && needToPlus;


        // 괄호가 있는 경우 분리하여 재귀적으로 계산
        if (needToSplit) {
            int splitPointIndex = findSplitPointIndex(exp);

            String firstExp = exp.substring(0, splitPointIndex);
            String secondExp = exp.substring(splitPointIndex + 1);

            char operator = exp.charAt(splitPointIndex);

            exp = Calc.run(firstExp) + " " + operator + " " + Calc.run(secondExp);

            return Calc.run(exp);
        }
        // 곱셈과 덧셈이 함께 있는 경우 복합 연산 처리
        else if (needToCompound) {
            String[] bits = exp.split(" \\+ ");

            String newExp = Arrays.stream(bits).mapToInt(Calc::run).mapToObj(e -> e + "").collect(Collectors.joining(" + "));

            return run(newExp);
        }
        // 덧셈 처리
        if (needToPlus) {
            exp = exp.replaceAll("- ", "+ -");

            String[] bits = exp.split(" \\+ ");

            int sum = 0;

            for (int i = 0; i < bits.length; i++) {
                sum += Integer.parseInt(bits[i]);
            }

            return sum;
        }
        // 곱셈 처리
        else if (needToMulti) {
            String[] bits = exp.split(" \\* ");

            int sum = 1;

            for (int i = 0; i < bits.length; i++) {
                sum *= Integer.parseInt(bits[i]);
            }

            return sum;
        }

        // 해석할 수 없는 경우 예외 처리
        throw new RuntimeException("해석 불가 : 올바른 계산식이 아니야");
    }


    // -( 패턴인지 확인하는 메서드
    private static boolean isCaseMinusBracket(String exp) {
        // -( 로 시작하는지?
        if (exp.startsWith("-(") == false) return false;

        // 괄호로 감싸져 있는지?
        int bracketsCount = 0;

        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);

            if (c == '(') {
                bracketsCount++;
            } else if (c == ')') {
                bracketsCount--;
            }
            if (bracketsCount == 0) {
                if (exp.length() - 1 == i) return true;
            }
        }

        return false;
    }

    // 연산자 위치를 찾는 메서드
    private static int findSplitPointIndex(String exp) {
        int index = findSplitPointIndexBy(exp, '+');

        if (index >= 0) return index;

        return findSplitPointIndexBy(exp, '*');
    }

    // 특정 연산자 위치를 찾는 메서드
    private static int findSplitPointIndexBy(String exp, char findChar) {
        int bracketsCount = 0;

        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);

            if (c == '(') {
                bracketsCount++;
            } else if (c == ')') {
                bracketsCount--;
            } else if (c == findChar) {
                if (bracketsCount == 0) return i;
            }
        }
        return -1;
    }

    // 외부 괄호 제거하는 메서드
    private static String stripOuterBrackets(String exp) {
        int outerBracketsCount = 0;

        while (exp.charAt(outerBracketsCount) == '(' && exp.charAt(exp.length() - 1 - outerBracketsCount) == ')') {
            outerBracketsCount++;
        }

        if (outerBracketsCount == 0) return exp;

        return exp.substring(outerBracketsCount, exp.length() - outerBracketsCount);
    }
}