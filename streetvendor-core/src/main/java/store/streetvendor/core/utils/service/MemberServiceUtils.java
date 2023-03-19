package store.streetvendor.core.utils.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.domain.member.MemberRepository;
import store.streetvendor.core.exception.DuplicatedException;
import store.streetvendor.core.exception.NotFoundException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberServiceUtils {

    public static Member findByMemberId(MemberRepository memberRepository, Long memberId) {
        Member member = memberRepository.findMemberById(memberId);
        validateMember(member, member.getId());
        return member;
    }
    private static void validateMember(Member member, Long memberId) {
        if (member == null) {
            throw new NotFoundException(String.format("(%s)에 해당하는 멤버는 존재하지 않습니다.", memberId));
        }
    }

    public static void validateNickName(MemberRepository memberRepository, String nickName) {
        Member member = memberRepository.findMemberByNickName(nickName);
        if (member != null) {
            throw new DuplicatedException(String.format("[%s]는 이미 사용중인 닉네임 입니다. 다른 닉네임을 입력해주세요.", nickName));
        }

    }

}
