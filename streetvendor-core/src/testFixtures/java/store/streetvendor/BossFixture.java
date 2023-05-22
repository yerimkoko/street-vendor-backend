package store.streetvendor;

import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.domain.member.MemberProvider;
import store.streetvendor.core.domain.member.MemberType;

public class BossFixture {

    public static Member boss() {
        return Member.builder()
            .name("뽀뽀")
            .nickName("뽀미누나")
            .phoneNumber("01023456789")
            .email("gochi97@naver.com")
            .profileUrl("s3.image")
            .provider(MemberProvider.GOOGLE)
            .memberType(MemberType.BOSS)
            .build();
    }
}
