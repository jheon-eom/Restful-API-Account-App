package com.ejh.accountapp.bank.valid;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegexTest {

    @Test
    @DisplayName("한글만 입력 가능 테스트")
    public void onlyKoreanTest() throws Exception {
        String value = "한글";
        boolean result = Pattern.matches("^[가-힣]+$", value);
        assertTrue(result);
    }

    @Test
    @DisplayName("한글 입력 불가 테스트")
    public void canNotKoreanTest() throws Exception {
        String value = "한글";
        boolean result = Pattern.matches("^[^가-힣]+$", value);
        assertFalse(result);
    }

    @Test
    @DisplayName("영어만 입력 가능 테스트")
    public void onlyEnglishTest() throws Exception {
        String value = "abcabc";
        boolean result = Pattern.matches("^[a-zA-Z]+$", value);
        assertTrue(result);
    }

    @Test
    @DisplayName("영어 입력 불가 테스트")
    public void canNotEnglishTest() throws Exception {
        String value = "abcabc";
        boolean result = Pattern.matches("^[^a-zA-Z]+$", value);
        assertFalse(result);
    }

    @Test
    @DisplayName("영어와 숫자만 입력 가능 테스트")
    public void onlyEnglishAndNumberTest() throws Exception {
        String value = "abcabc123";
        boolean result = Pattern.matches("^[a-zA-Z0-9]+$", value);
        assertTrue(result);
    }

}
