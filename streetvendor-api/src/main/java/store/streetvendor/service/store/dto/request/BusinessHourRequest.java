package store.streetvendor.service.store.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.store.BusinessHours;
import store.streetvendor.domain.domain.store.Days;
import store.streetvendor.domain.domain.store.Store;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class BusinessHourRequest {

    private LocalTime startTime;

    private LocalTime endTime;

    private Days days;

    @Builder
    public BusinessHourRequest(LocalTime startTime, LocalTime endTime, Days days) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.days = days;
    }

    public BusinessHours toEntity(Store store) {
        return BusinessHours.of(store, days, startTime, endTime);
    }

}
