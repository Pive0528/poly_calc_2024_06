package org.koreait;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MainTests {
    @Test
    void t1() {
        assertThat(Main.solution("hello", 1, 2)).isEqualTo("hlelo");
    }
}