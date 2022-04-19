package store.streetvendor.service.store.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.store.BusinessHours;
import store.streetvendor.domain.domain.store.Days;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
public class StoreOpeningTimeResponse {

    private Long storeId;

    private LocalTime startTime;

    private LocalTime endTime;

    private Days days;

    @Builder
    public StoreOpeningTimeResponse(Long storeId, LocalTime startTime, LocalTime endTime, Days days) {
        this.storeId = storeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.days = days;
    }

    public static StoreOpeningTimeResponse of(BusinessHours businessHours) {
        return StoreOpeningTimeResponse.builder()
            .startTime(businessHours.getOpeningTime().getStartTime())
            .endTime(businessHours.getOpeningTime().getEndTime())
            .days(businessHours.getDays())
            .build();
    }
}
