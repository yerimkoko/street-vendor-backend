package store.streetvendor;

import store.streetvendor.core.domain.boss.Boss;
import store.streetvendor.core.domain.member.MemberProvider;

public class BossFixture {

    public static Boss boss() {
        return Boss.builder()
            .name("뽀미누나")
            .phoneNumber("01023456789")
            .email("gochi97@naver.com")
            .profileUrl("s3.image")
            .accountNumber("1002-222-222222")
            .provider(MemberProvider.GOOGLE)
            .build();
    }
}
