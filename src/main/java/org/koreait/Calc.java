package org.koreait;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Calc {

    // 디버그 모드 플래그
    public static boolean debug = true;
    // run 메서드 호출 횟수
    public static int runCallCount = 0;

    public static int run(String exp) {
        return _run(exp, 0);
    }

    public static int _run(String exp, int depth) {
        // run 메서드 호출 횟수 증가
        runCallCount++;

        // 양 옆의 쓸데없는 공백 제거
        exp = exp.trim();
        // 괄호 제거
        exp = stripOuterBrackets(exp);

        // 만약에 -( 패턴이라면, 내가 갖고있는 코드는 해석할 수 없으므로 해석할 수 있는 형태로 수정
        int[] pos = null;
        while ((pos = findCaseMinusBracket(exp)) != null) {
            exp = changeMinusBracket(exp, pos[0], pos[1]);
        }

        exp = stripOuterBrackets(exp);

        // 디버그 모드인 경우 표시
        if (debug) {
            System.out.print(" ".repeat(depth * 4));
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
            exp = exp.replaceAll("- ", "+ -");
            int splitPointIndex = findSplitPointIndex(exp);

            String firstExp = exp.substring(0, splitPointIndex);
            String secondExp = exp.substring(splitPointIndex + 1);

            char operator = exp.charAt(splitPointIndex);

            exp = Calc._run(firstExp, depth + 1) + " " + operator + " " + Calc._run(secondExp, depth + 1);

            return Calc._run(exp, depth + 1);
        }

        // 곱셈과 덧셈이 함께 있는 경우 복합 연산 처리
        else if (needToCompound) {
            String[] bits = exp.split(" \\+ ");

            String newExp = Arrays.stream(bits)
                    .mapToInt(e -> Calc._run(e, depth + 1))
                    .mapToObj(e -> e + "")
                    .collect(Collectors.joining(" + "));

            return _run(newExp, depth + 1);
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

        // 해석할 수 없는 경우 예외 처리(거의 장식임)
        throw new RuntimeException("해석 불가 : 올바른 계산식이 아니야");
    }

    // -( 패턴인지 확인하는 메서드
    private static String changeMinusBracket(String exp, int startPos, int endPos) {
        String head = exp.substring(0, startPos);
        String body = "(" + exp.substring(startPos + 1, endPos + 1) + " * -1)";
        String tail = exp.substring(endPos + 1);

        exp = head + body + tail;

        return exp;
    }

    private static int[] findCaseMinusBracket(String exp) {
        for (int i = 0; i < exp.length() - 1; i++) {
            if (exp.charAt(i) == '-' && exp.charAt(i + 1) == '(') {
                // 발견

                int bracketsCount = 1;

                for (int j = i + 2; j < exp.length(); j++) {
                    char c = exp.charAt(j);

                    if (c == '(') {
                        bracketsCount++;
                    } else if (c == ')') {
                        bracketsCount--;
                    }

                    if (bracketsCount == 0) {
                        return new int[]{i, j};
                    }
                }
            }
        }

        return null;
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
        if (exp.charAt(0) == '(' && exp.charAt(exp.length() - 1) == ')') {
            int bracketsCount = 0;

            for (int i = 0; i < exp.length(); i++) {
                if (exp.charAt(i) == '(') {
                    bracketsCount++;
                } else if (exp.charAt(i) == ')') {
                    bracketsCount--;
                }

                if (bracketsCount == 0) {
                    if (exp.length() == i + 1) {
                        return stripOuterBrackets(exp.substring(1, exp.length() - 1));
                    }

                    return exp;
                }
            }
        }

        return exp;
    }
}