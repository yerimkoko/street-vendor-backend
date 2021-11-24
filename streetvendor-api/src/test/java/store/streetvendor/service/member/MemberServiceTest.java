package store.streetvendor.service.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.controller.dto.member.MemberSignUpRequestDto;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @AfterEach
    void cleanUp() {
        memberRepository.deleteAll();
    }

    @Test
    void 회원가입하기() {
        // given
        String email = "tokki@gmail.com";
        String nickName = "토끼";
        String name = "고토끼";
        String profileUrl = "234234tokki.jpg";

        MemberSignUpRequestDto requestDto = MemberSignUpRequestDto.testBuilder()
            .name(name)
            .nickName(nickName)
            .email(email)
            .profileUrl(profileUrl)
            .build();

        // when
        memberService.signUp(requestDto);

        // then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
        assertMember(members.get(0), name, nickName, email, profileUrl);
    }

    @Test
    void 닉네임이_중복인경우_에러가_발생한다() {
        // given
        String email = "tokki@gmail.com";
        String nickName = "토끼";
        String name = "고토끼";
        String profileUrl = "234234tokki.jpg";
        MemberSignUpRequestDto requestDto = new MemberSignUpRequestDto(name, nickName, email, profileUrl);
        memberRepository.save(Member.newGoogleInstance(name, nickName, email, profileUrl));

        // when & then
        assertThatThrownBy(() -> memberService.signUp(requestDto))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private void assertMember(Member member, String name, String nickName, String email, String profileUrl) {
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getNickName()).isEqualTo(nickName);
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getProfileUrl()).isEqualTo(profileUrl);
    }

}
