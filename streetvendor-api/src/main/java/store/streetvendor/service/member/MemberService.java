package store.streetvendor.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.service.member.dto.request.MemberSignUpRequestDto;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long signUp(MemberSignUpRequestDto requestDto) {
        validateDuplicatedNickName(requestDto.getNickName());
        Member member = memberRepository.save(requestDto.toEntity());
        return member.getId();
    }

    private void validateDuplicatedNickName(String nickName) {
        Member member = memberRepository.findMemberIdByNickName(nickName);
        if (member != null) {
            throw new IllegalArgumentException(String.format("(%s)는 중복된 닉네임 입니다. 다른 닉네임을 입력해주세요!", nickName));
        }
    }

}
