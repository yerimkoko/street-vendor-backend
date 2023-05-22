package store.streetvendor.service.boss;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.domain.member.MemberRepository;
import store.streetvendor.core.exception.DuplicatedException;
import store.streetvendor.service.dto.request.BossSignUpRequest;

@RequiredArgsConstructor
@Service
public class BossService {

    private final MemberRepository memberRepository;


    @Transactional
    public Long bossSignUp(BossSignUpRequest request) {
        validateEmail(request.getEmail());
        validateNickName(request.getNickName());
        Member member = memberRepository.save(request.toEntity());
        return member.getId();
    }

    private void validateEmail(String email) {
        Member boss = memberRepository.findMemberByEmail(email);
        if (boss != null) {
            throw new DuplicatedException(String.format("[%s]는 이미 존재하는 boss 입니다. 기존 아이디로 로그인해주세요.", email));
        }
    }

    private void validateNickName(String nickName) {
        Member boss = memberRepository.findMemberByNickName(nickName);
        if (boss != null) {
            throw new DuplicatedException(String.format("[%s]는 이미 존재하는 닉네임 입니다. 다른 닉네임을 입력해주세요.", nickName));
        }
    }

}
