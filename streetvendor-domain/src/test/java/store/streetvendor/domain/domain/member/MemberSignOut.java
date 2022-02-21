package store.streetvendor.domain.domain.member;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberSignOut {

    @Test
    void 회원탈퇴를_한다() {
        // given
        Member member = Member.newGoogleInstance("name", "nickName", "email", "profile");

        // when
        member.changeStatus();

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.SIGN_OUT);
    }

}
