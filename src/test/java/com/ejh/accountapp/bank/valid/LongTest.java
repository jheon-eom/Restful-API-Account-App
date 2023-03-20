package com.ejh.accountapp.bank.valid;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class LongTest {

    @Test
    @DisplayName("잔액 체크 테스트")
    void longBalanceCheckTest() {
        Long balance = 1000000000000L;
        Long balance2 = 0L;
        if (balance > 0L) {
            log.info("테스트 통과");
        } else {
            log.info("테스트 실패");
        }
        if (balance2 == 0L) {
            log.info("테스트 통과");
        } else {
            log.info("테스트 실패");
        }
    }
}
