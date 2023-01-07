package store.streetvendor.service;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.BossFixture;
import store.streetvendor.MemberFixture;
import store.streetvendor.core.domain.boss.Boss;
import store.streetvendor.core.domain.boss.BossRepository;
import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.domain.member.MemberRepository;


@SpringBootTest
public abstract class SetupBoss {

    @Autowired
    public MemberRepository memberRepository;

    @Autowired
    public BossRepository bossRepository;

    protected Member member = MemberFixture.member();

    protected Boss boss = BossFixture.boss();

    @BeforeEach
    void setupMember() {
        memberRepository.save(member);
        bossRepository.save(boss);
    }

    protected void cleanup() {
        memberRepository.deleteAll();
        bossRepository.deleteAll();
    }

}
