package store.streetvendor.domain.domain.admin.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import store.streetvendor.domain.domain.admin.Admin;

import static store.streetvendor.domain.domain.admin.QAdmin.admin;

@RequiredArgsConstructor
public class AdminRepositoryCustomImpl implements AdminRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Admin findByEmail(String email) {
        return queryFactory.selectFrom(admin)
            .where(
                admin.email.eq(email)
            )
            .fetchOne();
    }

    @Override
    public Admin findByAdminId(Long id) {
        return queryFactory.selectFrom(admin)
            .where(
                admin.id.eq(id)
            )
            .fetchOne();
    }
}
