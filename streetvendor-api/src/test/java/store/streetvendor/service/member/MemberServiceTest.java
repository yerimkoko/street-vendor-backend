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
        MemberSignUpRequestDto requestDto = new MemberSignUpRequestDto(name, nickName, email, profileUrl);

        // when
        memberService.signUp(requestDto);

        // then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
        assertThat(members.get(0).getEmail()).isEqualTo(email);
        assertThat(members.get(0).getName()).isEqualTo(name);
        assertThat(members.get(0).getProfileUrl()).isEqualTo(profileUrl);
        assertThat(members.get(0).getNickName()).isEqualTo(nickName);

    }

    @Test
    void 닉네임을_중복체크한다() {
        // given
        String email = "tokki@gmail.com";
        String nickName = "토끼";
        String name = "고토끼";
        String profileUrl = "234234tokki.jpg";
        MemberSignUpRequestDto requestDto = new MemberSignUpRequestDto(name, nickName, email, profileUrl);
        memberService.signUp(requestDto);

        // when & then
        assertThatThrownBy(() -> memberService.signUp(requestDto))
            .isInstanceOf(IllegalArgumentException.class);
    }

}
