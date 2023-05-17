package store.streetvendor.core.utils.dto.notification.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.notification.Notification;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class FaqResponse {

    private Long id;

    private String title;

    private String content;

    private String image;

    private LocalDateTime createTime;


    @Builder
    public FaqResponse(Long id, String title, String content, String image, LocalDateTime createTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.createTime = createTime;
    }

    public static FaqResponse of(Notification notification) {
        return FaqResponse.builder()
            .id(notification.getId())
            .title(notification.getTitle())
            .content(notification.getContent())
            .image(notification.getNotificationImage())
            .createTime(notification.getCreatedAt())
            .build();
    }
}
