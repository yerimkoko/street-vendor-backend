package store.streetvendor.service;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.MemberFixture;
import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.domain.member.MemberRepository;

import java.util.List;

@SpringBootTest
public abstract class SetupBoss {

    @Autowired
    public MemberRepository memberRepository;

    protected Member member = MemberFixture.member();

    protected Member boss = MemberFixture.boss();

    @BeforeEach
    void setupMember() {
        memberRepository.saveAll(List.of(member, boss));
    }

    protected void cleanup() {
        memberRepository.deleteAll();
    }

}
