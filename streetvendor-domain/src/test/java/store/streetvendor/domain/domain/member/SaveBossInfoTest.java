package store.streetvendor.domain.domain.member;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SaveBossInfoTest {

    @Test
    void 사장님_정보가_저장된다() {
        // given
       String bossName = "최토끼";
       String bossPhoneNumber = "010-2345-6789";
       Member member = Member.newGoogleInstance("토끼", "돼지", "street-vendor@gmail.com", "s3.sdfsdf");

       // when
        member.setBossNameAndNumber(bossName, bossPhoneNumber);

       // then
        assertThat(bossName).isEqualTo(member.getBossName());
        assertThat(bossPhoneNumber).isEqualTo(member.getPhoneNumber());
    }
}
