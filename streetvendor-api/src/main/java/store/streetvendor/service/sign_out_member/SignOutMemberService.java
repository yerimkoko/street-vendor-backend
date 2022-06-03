package store.streetvendor.service.sign_out_member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;
import store.streetvendor.domain.domain.sign_out_member.SignOutMember;
import store.streetvendor.domain.domain.sign_out_member.SignOutMemberRepository;
import store.streetvendor.service.member.MemberServiceUtils;

@Getter
@Service
@RequiredArgsConstructor
public class SignOutMemberService {

    private final SignOutMemberRepository signOutMemberRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public Long signOut(Long memberId) {
        Member member = MemberServiceUtils.findByMemberId(memberRepository, memberId);
        signOutMemberRepository.save(SignOutMember.of(member));
        return member.getId();
    }
}
