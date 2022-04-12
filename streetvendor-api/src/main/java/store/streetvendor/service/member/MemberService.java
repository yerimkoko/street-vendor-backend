package store.streetvendor.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.domain.domain.member.MemberStatus;
import store.streetvendor.exception.model.DuplicatedException;
import store.streetvendor.exception.model.NotFoundException;
import store.streetvendor.service.member.dto.request.MemberSignUpRequestDto;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;
import store.streetvendor.service.member.dto.response.MemberInfoResponse;

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
            throw new DuplicatedException(String.format("(%s)는 중복된 닉네임 입니다. 다른 닉네임을 입력해주세요!", nickName));
        }
    }

    @Transactional
    public Long signOut(Long memberId) {
        Member member = MemberServiceUtils.findByMemberId(memberRepository, memberId);
        member.changeStatus();
        return memberId;
    }

    @Transactional
    public MemberInfoResponse getMyInformation(Long memberId) {
        Member member = MemberServiceUtils.findByMemberId(memberRepository, memberId);
        return MemberInfoResponse.getInfo(member);
    }

}
