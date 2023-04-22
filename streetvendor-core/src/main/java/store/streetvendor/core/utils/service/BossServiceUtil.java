package store.streetvendor.core.utils.service;

import store.streetvendor.core.domain.boss.Boss;
import store.streetvendor.core.domain.boss.BossRepository;
import store.streetvendor.core.exception.NotFoundException;

public class BossServiceUtil {

    public static Boss findBossById(BossRepository bossRepository, Long bossId) {
        Boss boss = bossRepository.findByBossId(bossId);
        if (boss == null) {
            throw new NotFoundException(String.format("[%s]에 해당하는 사장님은 존재하지 않습니다.", bossId));
        }
        return boss;
    }

    public static void validateBoss(Boss boss, Long bossId) {
        if (boss == null) {
            throw new NotFoundException(String.format("[%s]에 해당하는 사장님은 존재하지 않습니다.", bossId));
        }
    }
}
