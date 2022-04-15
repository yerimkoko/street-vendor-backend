package store.streetvendor.service.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.member.Member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class MemberSignUpRequestDto {

    private String name;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 1, max = 8, message = "닉네임은 1~8자 이여야 합니다.")
    private String nickName;

    private String email;

    private String profileUrl;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public MemberSignUpRequestDto(String name, String nickName, String email, String profileUrl) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.profileUrl = profileUrl;
    }

    public Member toEntity() {
        return Member.newGoogleInstance(name, nickName, email, profileUrl);
    }
}
