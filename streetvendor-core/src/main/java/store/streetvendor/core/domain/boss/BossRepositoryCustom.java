package store.streetvendor.core.domain.boss;

public interface BossRepositoryCustom {
    Boss findByBossId(Long bossId);

    Boss findByEmail(String email);

    Boss findByNickName(String nickName);
}
