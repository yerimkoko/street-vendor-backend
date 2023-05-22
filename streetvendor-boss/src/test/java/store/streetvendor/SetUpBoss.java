package store.streetvendor;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.domain.member.MemberRepository;

@SpringBootTest
public abstract class SetUpBoss {

    @Autowired
    public MemberRepository memberRepository;

    protected Member boss = BossFixture.boss();

    @BeforeEach
    void setUpBoss() {
        memberRepository.save(boss);

    }

    protected void cleanup() {
        memberRepository.deleteAll();
    }

}
