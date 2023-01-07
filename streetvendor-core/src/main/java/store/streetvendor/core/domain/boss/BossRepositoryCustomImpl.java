package store.streetvendor.core.domain.boss;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static store.streetvendor.core.domain.boss.QBoss.boss;

@RequiredArgsConstructor
public class BossRepositoryCustomImpl implements BossRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Boss findByBossId(Long bossId) {
        return jpaQueryFactory.selectFrom(boss)
            .where(boss.id.eq(bossId))
            .fetchOne();
    }

    @Override
    public Boss findByEmail(String email) {
        return jpaQueryFactory.selectFrom(boss)
            .where(boss.email.eq(email))
            .fetchOne();
    }

    @Override
    public Boss findByNickName(String nickName) {
        return jpaQueryFactory.selectFrom(boss)
            .where(boss.nickName.eq(nickName))
            .fetchOne();
    }
}
