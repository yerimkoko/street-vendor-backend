package store.streetvendor.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.exception.model.DuplicatedException;
import store.streetvendor.service.member.dto.request.MemberSaveBossInfoRequest;
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
        validateDuplicatedEmail(requestDto.getEmail());
        Member member = memberRepository.save(requestDto.toEntity());
        return member.getId();
    }


    @Transactional
    public Long signOut(Long memberId) {
        Member member = MemberServiceUtils.findByMemberId(memberRepository, memberId);
        member.changeStatus();
        return memberId;
    }

    @Transactional(readOnly = true)
    public MemberInfoResponse getMyInformation(Long memberId) {
        Member member = MemberServiceUtils.findByMemberId(memberRepository, memberId);
        return MemberInfoResponse.getInfo(member);
    }

    @Transactional
    public void saveMemberBossInfo(Long memberId, MemberSaveBossInfoRequest request) {
        Member member = MemberServiceUtils.findByMemberId(memberRepository, memberId);
        member.setBossNameAndNumber(request.getBossName(), request.getBossPhoneNumber());
    }

    @Transactional(readOnly = true)
    public void checkBoss(Long memberId) {
        MemberServiceUtils.findByBossId(memberRepository, memberId);
    }

    private void validateDuplicatedNickName(String nickName) {
        Member member = memberRepository.findActiveMemberIdByNickName(nickName);
        if (member != null) {
            throw new DuplicatedException(String.format("(%s)는 이미 사용중인 닉네임 입니다. 다른 닉네임을 입력해주세요.", nickName));
        }
    }

    private void validateDuplicatedEmail(String email) {
        Member member = memberRepository.findActiveMemberIdByEmail(email);
        if (member != null) {
            throw new DuplicatedException(String.format("(%s)는 이미 가입된 회원입니다. 기존 이메일로 로그인해주세요.", email));
        }
    }

}
