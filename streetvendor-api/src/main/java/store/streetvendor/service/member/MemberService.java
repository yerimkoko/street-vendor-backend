package store.streetvendor.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.controller.dto.member.MemberSignUpRequestDto;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long signUp(MemberSignUpRequestDto requestDto) {
        Member doubleCheckNickName = memberRepository.findMemberIdByNickName(requestDto.getNickName());
        if (doubleCheckNickName != null) {
            throw new IllegalArgumentException(String.format("(%s)는 중복된 닉네임 입니다. 다른 닉네임을 입력해주세요!", requestDto.getNickName()));
        }
        Member member = memberRepository.save(Member.newGoogleInstance(requestDto.getName(), requestDto.getNickName(), requestDto.getEmail(),
            requestDto.getProfileUrl()));
        return member.getId();
    }


}
