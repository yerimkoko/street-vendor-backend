package store.streetvendor.core.domain.store.projection;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
public class StoreProjectionCustomImpl implements StoreProjectionCustom {

    private final JPAQueryFactory jpaQueryFactory;

}
