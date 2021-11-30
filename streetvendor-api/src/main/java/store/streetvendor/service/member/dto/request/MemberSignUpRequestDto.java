package store.streetvendor.service.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.member.Member;

@Getter
@NoArgsConstructor
public class MemberSignUpRequestDto {

    private String name;

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
