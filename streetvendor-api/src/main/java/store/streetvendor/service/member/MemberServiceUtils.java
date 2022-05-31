package store.streetvendor.service.member;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;
import store.streetvendor.exception.model.NotFoundException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberServiceUtils {

    public static Member findByMemberId(MemberRepository memberRepository, Long memberId) {
        Member member = memberRepository.findMemberById(memberId);
        validateMember(member, member.getId());
        return member;
    }

    public static Member findByBossId(MemberRepository memberRepository, Long memberId) {
        Member member = findByMemberId(memberRepository, memberId);
        validateBoss(member);
        return member;
    }

    private static void validateMember(Member member, Long memberId) {
        if (member == null) {
            throw new NotFoundException(String.format("(%s)에 해당하는 멤버는 존재하지 않습니다.", memberId));
        }
    }

    public static void validateBoss(Member member) {
        if (member.getBossName() == null || member.getPhoneNumber() == null) {
            throw new NotFoundException("사장님 등록을 먼저 해 주세요.");
        }
    }

}
