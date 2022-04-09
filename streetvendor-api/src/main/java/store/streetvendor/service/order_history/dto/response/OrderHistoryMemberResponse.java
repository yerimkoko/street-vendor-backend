package store.streetvendor.service.order_history.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.member.Member;

@Getter
@NoArgsConstructor
public class OrderHistoryMemberResponse {

    private Long memberId;

    private String name;

    private String email;

    private String profileUrl;

    @Builder
    public OrderHistoryMemberResponse(Long memberId, String name, String email, String profileUrl) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.profileUrl = profileUrl;
    }

    public static OrderHistoryMemberResponse of(Member member) {
        return OrderHistoryMemberResponse.builder()
            .email(member.getEmail())
            .name(member.getName())
            .email(member.getEmail())
            .profileUrl(member.getProfileUrl())
            .build();
    }
}
