package com.ejh.accountapp.bank.dto.user;

import com.ejh.accountapp.bank.domain.user.User;
import lombok.Getter;

@Getter
public class JoinResponseDto {
    private Long id;
    private String username;
    private String email;

    public JoinResponseDto(User joinUser) {
        id = joinUser.getId();
        username = joinUser.getUsername();
        email = joinUser.getEmail();
    }
}
