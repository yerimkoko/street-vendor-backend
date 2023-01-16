package store.streetvendor.service.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class NotificationListRequest {

    private LocalDate startDate;

    private LocalDate endDate;

    public NotificationListRequest(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDateEq(startDate);
        this.endDate = endDateEq(endDate);
    }

    private LocalDate startDateEq(LocalDate startDate) {
        if (startDate == null) {
            return LocalDate.now().minusDays(1);
        }
        return startDate.minusDays(1);
    }

    private LocalDate endDateEq(LocalDate endDate) {
        if (endDate == null) {
            return LocalDate.of(2099, 12, 31);
        }
        return endDate.plusDays(1);
    }

}
