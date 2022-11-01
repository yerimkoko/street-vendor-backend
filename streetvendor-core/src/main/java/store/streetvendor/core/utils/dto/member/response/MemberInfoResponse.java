package store.streetvendor.core.utils.dto.member.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.member.Member;

@Getter
@NoArgsConstructor
public class MemberInfoResponse {
    private Long memberId;

    private String email;

    private String nickName;

    private String profileUrl;

    @Builder
    public MemberInfoResponse(Long memberId, String email, String nickName, String profileUrl) {
        this.memberId = memberId;
        this.email = email;
        this.nickName = nickName;
        this.profileUrl = profileUrl;
    }

    public static MemberInfoResponse getInfo(Member member) {
        return MemberInfoResponse.builder()
            .email(member.getEmail())
            .memberId(member.getId())
            .profileUrl(member.getProfileUrl())
            .nickName(member.getNickName())
            .build();
    }
}
