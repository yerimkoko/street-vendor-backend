package store.streetvendor.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignOutMemberRequest {

    private Long memberId;

    private Long adminId;

    public SignOutMemberRequest(Long memberId, Long adminId) {
        this.memberId = memberId;
        this.adminId = adminId;
    }
}
