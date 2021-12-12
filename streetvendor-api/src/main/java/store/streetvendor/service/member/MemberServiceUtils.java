package store.streetvendor.service.member;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class MemberServiceUtils {

    static void validateDuplicatedNickName(MemberRepository memberRepository, String nickName) {
        Member member = memberRepository.findMemberIdByNickName(nickName);
        if (member != null) {
            throw new IllegalArgumentException(String.format("(%s)는 중복된 닉네임 입니다. 다른 닉네임을 입력해주세요!", nickName));
        }
    }

}
