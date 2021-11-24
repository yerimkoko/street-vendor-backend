package store.streetvendor.controller.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSignUpRequestDto {

    private String name;

    private String nickName;

    private String email;

    private String profileUrl;

    public MemberSignUpRequestDto(String name, String nickName, String email, String profileUrl) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.profileUrl = profileUrl;

    }
}
