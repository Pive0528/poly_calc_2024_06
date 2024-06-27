package org.koreait;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CalcTests {
    @Test
    @DisplayName("1 + 1 == 2")
    void t1() {
        assertThat(Calc.run("1 + 1")).isEqualTo(2);
    }

    @Test
    @DisplayName("2 + 1 == 3")
    void t2() {
        assertThat(Calc.run("2 + 1")).isEqualTo(3);
    }

    @Test
    @DisplayName("2 + 2 == 4")
    void t3() {
        assertThat(Calc.run("2 + 2")).isEqualTo(4);
    }

    @Test
    @DisplayName("1000 + 200 == 1200")
    void t4() {
        assertThat(Calc.run("1000 + 200")).isEqualTo(1200);
    }

    @Test
    @DisplayName("50 - 30 == 20")   // 이름임. 별상관 ㄴ
    void t5() {
        assertThat(Calc.run("50 - 30")).isEqualTo(20);
    }

    @Test
    @DisplayName("100 - 20 == 80")   // 이름임. 별상관 ㄴ
    void t6() {
        assertThat(Calc.run("100 - 20")).isEqualTo(80);
    }

    @Test
    @DisplayName("10 + 20 + 30 == 60")   // 이름임. 별상관 ㄴ
    void t7() {
        assertThat(Calc.run("10 + 20 + 30")).isEqualTo(60);
    }

    @Test
    @DisplayName("10 - 20 + 30 == 20")   // 이름임. 별상관 ㄴ
    void t8() {
        assertThat(Calc.run("10 - 20 + 30")).isEqualTo(20);
    }

    @Test
    @DisplayName("10 - 10 - 10 - 10 + 10 + 10 - 10")   // 이름임. 별상관 ㄴ
    void t9() {
        assertThat(Calc.run("10 - 10 - 10 - 10 + 10 + 10 - 10")).isEqualTo(-10);
    }

    @Test
    @DisplayName("10 * 10 * 10")   // 이름임. 별상관 ㄴ
    void t10() {
        assertThat(Calc.run("10 * 10")).isEqualTo(100);
    }

    @Test
    @DisplayName("10 + 5 * 2")   // 이름임. 별상관 ㄴ
    void t11() {
        assertThat(Calc.run("10 + 5 * 2")).isEqualTo(20);
    }

    @Test
    @DisplayName("20 + 10 + 5 * 2")   // 이름임. 별상관 ㄴ
    void t12() {
        assertThat(Calc.run("20 + 10 + 5 * 2")).isEqualTo(40);
    }

    @Test
    @DisplayName("(10 + 20)")   // 이름임. 별상관 ㄴ
    void t13() {
        assertThat(Calc.run("(10 + 20)")).isEqualTo(30);
    }

    @Test
    @DisplayName("((10 + 20))")   // 이름임. 별상관 ㄴ
    void t14() {
        assertThat(Calc.run("((10 + 20))")).isEqualTo(30);
    }

    @Test
    @DisplayName("(((10 + 20)))")   // 이름임. 별상관 ㄴ
    void t15() {
        assertThat(Calc.run("(((10 + 20)))")).isEqualTo(30);
    }

    @Test
    @DisplayName("(20 + 20) + 20")   // 이름임. 별상관 ㄴ
    void t16() {
        assertThat(Calc.run("(20 + 20) + 20")).isEqualTo(60);
    }

    @Test
    @DisplayName("(10 + 20) * 3")   // 이름임. 별상관 ㄴ
    void t17() {
        assertThat(Calc.run("(10 + 20) * 3")).isEqualTo(90);
    }

    @Test
    @DisplayName("10 + (10 + 5)")   // 이름임. 별상관 ㄴ
    void t18() {
        assertThat(Calc.run("10 + (10 + 5)")).isEqualTo(25);
    }

    @Test
    @DisplayName("-(10 + 20)")   // 이름임. 별상관 ㄴ
    void t19() {
        assertThat(Calc.run("-(10 + 20)")).isEqualTo(-15);
    }

    @Test
    @DisplayName("-(8 + 2) * -(7 + 3) +5")   // 이름임. 별상관 ㄴ
    void t20() {
        assertThat(Calc.run("-(8 + 2) * -(7 + 3) + 5")).isEqualTo(105);
    }

    @Test
    @DisplayName("5 - (1 + 5)")   // 이름임. 별상관 ㄴ
    void t21() {
        assertThat(Calc.run("5 - (1 + 5)")).isEqualTo(-1);
    }


    @Test
    @DisplayName("3 * 1 + (1 - (4 * 1 - (1 - 1)))")   // 이름임. 별상관 ㄴ
    void t22() {
        assertThat(Calc.run("3 * 1 + (1 - (4 * 1 - (1 - 1)))")).isEqualTo(0);
    }



}