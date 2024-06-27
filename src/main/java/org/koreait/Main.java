package org.koreait;
import java.util.Arrays;

public class Main {
    public static String solution(String my_string) {
        String[] answer = my_string.split("");
        int[] arr = new int[answer.length];
        String answer2 = "";
        Arrays.sort(arr);
        for (int i = 0; i<arr.length; i++) {
            arr[i] = Integer.parseInt(answer[i]);
        }   // 숫자배열에 split으로 나눈 문자열을 숫자로 넣기

        for (int i = 0; i<arr.length; i++) {
            if (arr[i]>=65) {
                arr[i]+=32;
            }
        }   // 소문자(숫자)로 바꿔주기
        for (int i = 0; i<arr.length; i++) {
            answer2=Integer.toString(arr[i]);
        }

        return answer2;
    }
}