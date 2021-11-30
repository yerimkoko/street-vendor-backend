package store.streetvendor.service.store.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import store.streetvendor.domain.domain.store.Store;

import java.time.LocalTime;

@NoArgsConstructor
@Getter
public class StoreResponseDto {

    private Long storeId;

    private Long bossId;

    private String name;

    private String pictureUrl;

    private String location;

    private String description;

    private LocalTime startTime;

    private LocalTime endTime;


    @Builder
    public StoreResponseDto(Long storeId, Long bossId, String name, String pictureUrl, String location, String description,
                            LocalTime startTime, LocalTime endTime) {
        this.storeId = storeId;
        this.bossId = bossId;
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.location = location;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static StoreResponseDto of(Store store) {
        return StoreResponseDto.builder()
            .storeId(store.getId())
            .bossId(store.getMemberId())
            .name(store.getName())
            .pictureUrl(store.getPictureUrl())
            .location(store.getLocation())
            .description(store.getDescription())
            .startTime(store.getOpeningTime().getStartTime())
            .endTime(store.getOpeningTime().getEndTime())
            .build();
    }
}
