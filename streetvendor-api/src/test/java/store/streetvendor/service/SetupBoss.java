package store.streetvendor.service;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.BossFixture;
import store.streetvendor.MemberFixture;
import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.domain.member.MemberRepository;


@SpringBootTest
public abstract class SetupBoss {

    @Autowired
    public MemberRepository memberRepository;

    protected Member member = MemberFixture.member();

    protected Member boss = BossFixture.boss();

    @BeforeEach
    void setupMember() {
        memberRepository.save(member);
        memberRepository.save(boss);
    }

    protected void cleanup() {
        memberRepository.deleteAll();
    }

}
