package store.streetvendor.service.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.member.Member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
public class BossSignUpRequest {

    @NotBlank
    private String name;

    @NotBlank(message = "전화번호를 입력해주세요.")
    private String phoneNumber;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 8, message = "닉네임은 2~8자 이어야 합니다.")
    private String nickName;

    @Email(message = "이메일 형식에 맞게 적어주세요.")
    private String email;

    private String profileUrl;

    public BossSignUpRequest(String name,  String phoneNumber, String nickName, String email, String profileUrl) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.nickName = nickName;
        this.email = email;
        this.profileUrl = profileUrl;
    }

    public Member toEntity() {
        return Member.newGoogleBossInstance(name, nickName, email, profileUrl, phoneNumber);
    }

}
