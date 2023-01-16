package store.streetvendor.service.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.admin.Admin;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class AdminSignUpRequest {

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickName;

    @Email(message = "이메일 형식에 맞게 적어주세요.")
    private String email;

    public AdminSignUpRequest(String nickName, String email) {
        this.nickName = nickName;
        this.email = email;
    }

    public Admin toEntity() {
        return Admin.newAdmin(email, nickName);
    }
}
