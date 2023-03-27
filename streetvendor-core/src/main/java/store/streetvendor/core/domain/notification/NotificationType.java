package store.streetvendor.core.domain.notification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NotificationType {
    NOTIFICATION("공지사항"),
    EVENT("이벤트"),
    FAQ_BOSS("FAQ_사장님앱 전용"),
    FAQ_USER("FAQ_유저앱 전용")
    ;

    public final String description;
}
