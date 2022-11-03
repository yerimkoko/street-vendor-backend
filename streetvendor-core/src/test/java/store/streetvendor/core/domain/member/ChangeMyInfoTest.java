package store.streetvendor.core.domain.member;

import org.junit.jupiter.api.Test;
import store.streetvendor.MemberFixture;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ChangeMyInfoTest {

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

    @Test
    void 닉네임을_변경한다() {
        // given
        Member member = MemberFixture.member();
        String nickname = "뽀미이";

        // when
        member.changeNickName(nickname);

        // then
        assertThat(member.getNickName()).isEqualTo(nickname);
    }





}
