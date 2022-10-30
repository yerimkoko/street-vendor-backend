package store.streetvendor.core.service.utils.dto.order_history.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.service.utils.dto.order_history.dto.response.OrderHistoryMemberResponse;

@Getter
@NoArgsConstructor
public class OrderHistoryMemberRequest {

    private Member member;

    @Builder
    public OrderHistoryMemberRequest(Member member) {
        this.member = member;
    }

    public OrderHistoryMemberResponse toEntity(Member member){
        return OrderHistoryMemberResponse.builder()
            .memberId(member.getId())
            .name(member.getName())
            .email(member.getEmail())
            .profileUrl(member.getProfileUrl())
            .build();
    }


}
