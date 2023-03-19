package store.streetvendor.core.domain.member;

import org.junit.jupiter.api.Test;
import store.streetvendor.core.domain.sign_out_member.SignOutMember;

import static org.assertj.core.api.Assertions.assertThat;

class SignOutMemberTest {

    @Test
    void 회원탈퇴를_한다() {
        // given
        Member member = Member.newGoogleInstance("name", "nickName", "email", "profile");

        // when
        SignOutMember signOutMember = member.signOut();

        // then
        assertThat(signOutMember.getMemberId()).isEqualTo(member.getId());
        assertThat(signOutMember.getEmail()).isEqualTo(member.getEmail());
        assertThat(signOutMember.getNickName()).isEqualTo(member.getNickName());
        assertThat(signOutMember.getProfileUrl()).isEqualTo(member.getProfileUrl());
        assertThat(signOutMember.getProvider()).isEqualTo(member.getProvider());

    }
}
