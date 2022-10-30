package store.streetvendor.core.domain.store.star;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static store.streetvendor.core.domain.store.star.QStar.star;


@RequiredArgsConstructor
public class StarRepositoryCustomImpl implements StarRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Star findByStarId(Long starId) {
        return jpaQueryFactory.selectFrom(star)
            .where(star.id.eq(starId))
            .fetchOne();
    }

    @Override
    public List<Star> findMyStars(Long memberId) {
        return jpaQueryFactory.selectFrom(star)
            .where(star.memberId.eq(memberId))
            .fetch();
    }
}
