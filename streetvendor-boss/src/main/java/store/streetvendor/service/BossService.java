package store.streetvendor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.core.domain.boss.Boss;
import store.streetvendor.core.domain.boss.BossRepository;
import store.streetvendor.core.exception.DuplicatedException;
import store.streetvendor.service.dto.request.BossSignUpRequest;

@RequiredArgsConstructor
@Service
public class BossService {

    private final BossRepository bossRepository;

    @Transactional
    public Long bossSignUp(BossSignUpRequest request) {
        validateEmail(request.getEmail());
        validateNickName(request.getNickName());
        Boss boss = bossRepository.save(request.toEntity());
        return boss.getId();
    }

    private void validateEmail(String email) {
        Boss boss = bossRepository.findByEmail(email);
        if (boss != null) {
            throw new DuplicatedException(String.format("[%s]는 이미 존재하는 boss 입니다. 기존 아이디로 로그인해주세요.", email));
        }
    }

    private void validateNickName(String nickName) {
        Boss boss = bossRepository.findByNickName(nickName);
        if (boss != null) {
            throw new DuplicatedException(String.format("[%s]는 이미 존재하는 닉네임 입니다. 다른 닉네임을 입력해주세요.", nickName));
        }
    }
}
