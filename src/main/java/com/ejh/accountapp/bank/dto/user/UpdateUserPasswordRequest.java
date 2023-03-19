package com.ejh.accountapp.bank.dto.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateUserPasswordRequest {
    @NotEmpty(message = "현재 password는 필수입니다.")
    @Size(min = 4, max = 20)
    private String currentPassword;
    @NotEmpty(message = "변경할 패스워드는 필수입니다.")
    @Size(min = 4, max = 20)
    private String newPassword;
}
