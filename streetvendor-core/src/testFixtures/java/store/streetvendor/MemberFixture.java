package store.streetvendor;

import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.domain.member.MemberProvider;

public class MemberFixture {

    public static Member member() {
        return Member.builder()
            .name("뽀미")
            .nickName("뽀뽀")
            .email("gochi97@naver.com")
            .profileUrl("https://rabbit.com")
            .provider(MemberProvider.GOOGLE)
            .build();
    }


}
