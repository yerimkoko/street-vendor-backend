package store.streetvendor.domain.domain.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;

	@Test
	void QueryDSL_테스트() {
		// given
		Member member = new Member("토끼");
		memberRepository.save(member);

		// when
		Member findMember = memberRepository.findMemberById(member.getId());

		// then
		assertThat(findMember.getName()).isEqualTo(member.getName());
	}

}