package store.streetvendor.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;
import store.streetvendor.domain.service.utils.MemberServiceUtils;
import store.streetvendor.domain.domain.sign_out_member.SignOutMemberRepository;
import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.repository.StoreRepository;
import store.streetvendor.domain.domain.store.StoreSalesStatus;
import store.streetvendor.domain.domain.model.exception.DuplicatedException;
import store.streetvendor.service.member.dto.request.MemberSaveBossInfoRequest;
import store.streetvendor.service.member.dto.request.MemberSignUpRequestDto;
import store.streetvendor.service.member.dto.response.MemberInfoResponse;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final SignOutMemberRepository signOutMemberRepository;

    private final StoreRepository storeRepository;

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
        List<Store> stores = storeRepository.findStoreByBossId(memberId);
        Store openedStore = getOpenedStores(stores);
        if (openedStore != null)
            throw new DuplicatedException(String.format("<%s> 가게가 영업중입니다. 영업 종료를 먼저 해 주세요.", openedStore.getId()));
        signOutMemberRepository.save(member.signOut());
        memberRepository.delete(member);
        return memberId;
    }


    @Transactional(readOnly = true)
    public MemberInfoResponse getMyInformation(Long memberId) {
        Member member = MemberServiceUtils.findByMemberId(memberRepository, memberId);
        return MemberInfoResponse.getInfo(member);
    }

    @Transactional
    public void changeProfileImage(Long memberId, String profileUrl) {
        Member member = MemberServiceUtils.findByMemberId(memberRepository, memberId);
        member.changeProfileUrl(profileUrl);
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
        Member member = memberRepository.findMemberIdByNickName(nickName);
        if (member != null) {
            throw new DuplicatedException(String.format("(%s)는 이미 사용중인 닉네임 입니다. 다른 닉네임을 입력해주세요.", nickName));
        }
    }

    private void validateDuplicatedEmail(String email) {
        Member member = memberRepository.findMemberIdByEmail(email);
        if (member != null) {
            throw new DuplicatedException(String.format("(%s)는 이미 가입된 회원입니다. 기존 이메일로 로그인해주세요.", email));
        }
    }

    private Store getOpenedStores(List<Store> stores) {
        return stores.stream()
            .filter(store -> store.getSalesStatus()
                .equals(StoreSalesStatus.OPEN))
            .findFirst()
            .orElse(null);
    }

}
