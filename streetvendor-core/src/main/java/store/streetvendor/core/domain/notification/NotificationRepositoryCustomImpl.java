package store.streetvendor.core.domain.notification;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static store.streetvendor.core.domain.notification.QNotification.notification;

@RequiredArgsConstructor
public class NotificationRepositoryCustomImpl implements NotificationRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Notification findByNotificationId(Long id) {
        return jpaQueryFactory.selectFrom(notification)
            .where(notification.id.eq(id))
            .fetchOne();
    }

    @Override
    public List<Notification> findNotificationByStartTimeAndEndTime(LocalDate startDate, LocalDate endDate) {
        return jpaQueryFactory.selectFrom(notification)
            .where(notification.startDate.after(startDate),
                notification.endDate.before(endDate))
            .orderBy(notification.startDate.asc())
            .fetch();
    }

}
