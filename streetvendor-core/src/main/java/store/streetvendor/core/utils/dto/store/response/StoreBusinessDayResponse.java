package store.streetvendor.core.utils.dto.store.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.store.BusinessHours;
import store.streetvendor.core.domain.store.Days;

import java.time.LocalTime;

@NoArgsConstructor
@Getter
public class StoreBusinessDayResponse {

    private Days days;

    private LocalTime startTime;

    private LocalTime endTime;

    @Builder
    public StoreBusinessDayResponse(Days days, LocalTime startTime, LocalTime endTime) {
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static StoreBusinessDayResponse of(BusinessHours businessHours) {
        return StoreBusinessDayResponse.builder()
            .days(businessHours.getDays())
            .startTime(businessHours.getOpeningTime().getStartTime())
            .endTime(businessHours.getOpeningTime().getEndTime())
            .build();
    }
}
