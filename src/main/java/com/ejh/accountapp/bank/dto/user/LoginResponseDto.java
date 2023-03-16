package com.ejh.accountapp.bank.dto.user;

import com.ejh.accountapp.bank.domain.user.User;
import com.ejh.accountapp.bank.util.CommonDateUtil;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private Long id;
    private String username;
    private String createdAt;

    public LoginResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.createdAt = CommonDateUtil.toStringFormat(user.getCreatedAt());
    }
}
