package store.streetvendor.core.domain.member;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ChangeMemberProfileUrlTest {

    @Test
    void 프로필사진을_수정한다() {
        // given
        Member member = Member.newGoogleInstance("name", "nickName", "email", "profileUrl");
        String profileUrl = "tokki";

        // when
        member.changeProfileUrl(profileUrl);

        // then
        assertThat(member.getProfileUrl()).isEqualTo(profileUrl);

    }





}
