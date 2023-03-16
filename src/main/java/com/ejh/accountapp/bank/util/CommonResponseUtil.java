package com.ejh.accountapp.bank.util;

import com.ejh.accountapp.bank.dto.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CommonResponseUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void success(HttpServletResponse response, String msg, Object dto) {
        try {
            ResponseDto<?> responseDto = new ResponseDto<>(1, msg, dto);
            String responseBody = objectMapper.writeValueAsString(responseDto);
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(200);
            response.getWriter().println(responseBody);
        } catch (Exception e) {
            log.error("can not parsing");
        }
    }

    public static void fail(HttpServletResponse response, String msg, HttpStatus httpStatus) {
        try {
            ResponseDto<?> responseDto = new ResponseDto<>(-1, msg, null);
            String responseBody = objectMapper.writeValueAsString(responseDto);
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(httpStatus.value());
            response.getWriter().println(responseBody);
        } catch (Exception e) {
            log.error("can not parsing");
        }
    }

}
