package store.streetvendor.service.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.MemberFixture;
import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.domain.member.MemberRepository;
import store.streetvendor.core.domain.sign_out_member.SignOutMember;
import store.streetvendor.core.domain.sign_out_member.SignOutMemberRepository;
import store.streetvendor.core.exception.ConflictException;
import store.streetvendor.core.exception.DuplicatedException;
import store.streetvendor.core.utils.dto.member.request.MemberSignUpRequestDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SignOutMemberRepository signOutMemberRepository;

    @Autowired
    private MemberService memberService;

    @AfterEach
    void cleanUp() {
        memberRepository.deleteAll();
        signOutMemberRepository.deleteAll();
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
    void 회원을_탈퇴하면_member_에서_사라지고_회원탈퇴_테이블에_저장된다() {
        // given
        Member member = createMemberInstance();

        // when
        memberService.signOut(member.getId());

        // then
        List<Member> members = memberRepository.findAll();
        List<SignOutMember> signOutMembers = signOutMemberRepository.findAll();
        assertThat(members).hasSize(0);
        assertThat(signOutMembers).hasSize(1);

    }

    @Test
    void 닉네임이_중복인경우_에러가_발생한다() {
        // given
        Member member = createMemberInstance();
        MemberSignUpRequestDto requestDto = new MemberSignUpRequestDto(member.getName(), member.getNickName(), member.getEmail(), member.getProfileUrl());


        // when & then
        assertThatThrownBy(() -> memberService.signUp(requestDto))
            .isInstanceOf(DuplicatedException.class);
    }

    @Test
    void 닉네임을_변경한다() {
        // given
        Member member = MemberFixture.member();
        memberRepository.save(member);
        String newNickName = "뽀미이이";

        // when
        memberService.changeNickName(member.getId(), newNickName);

        // then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
        assertThat(members.get(0).getNickName()).isEqualTo(newNickName);
    }

    @Test
    void 닉네임이_변경되지_않을때() {
        // given
        Member existedMember = createMemberInstance();
        Member member = MemberFixture.member();
        memberRepository.save(member);

        // when & then
        assertThatThrownBy(() -> memberService.changeNickName(member.getId(), existedMember.getNickName()))
            .isInstanceOf(DuplicatedException.class);
    }


    private Member createMemberInstance() {
        String email = "tokki@gmail.com";
        String nickName = "토끼";
        String name = "고토끼";
        String profileUrl = "234234tokki.jpg";

        return memberRepository.save(Member.newGoogleInstance(name, nickName, email, profileUrl));

    }

    private void assertMember(Member member, String name, String nickName, String email, String profileUrl) {
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getNickName()).isEqualTo(nickName);
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getProfileUrl()).isEqualTo(profileUrl);
    }

}
