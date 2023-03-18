package com.ejh.accountapp.bank.web;

import com.ejh.accountapp.bank.dto.ResponseDto;
import com.ejh.accountapp.bank.dto.user.JoinRequestDto;
import com.ejh.accountapp.bank.dto.user.JoinResponseDto;
import com.ejh.accountapp.bank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserApiController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid JoinRequestDto joinRequestDto, BindingResult bindingResult) {
        JoinResponseDto joinUser = userService.join(joinRequestDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "회원가입 성공", joinUser), HttpStatus.CREATED);
    }
}
