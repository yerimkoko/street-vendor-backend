package store.streetvendor.domain.domain.store;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class OpeningTime {

    private LocalTime startTime;

    private LocalTime endTime;

    private OpeningTime(LocalTime startTime, LocalTime endTime) {
        validateOpeningTime(startTime, endTime);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static OpeningTime of(LocalTime startTime, LocalTime endTime) {
        return new OpeningTime(startTime, endTime);
    }

    private void validateOpeningTime(LocalTime startTime, LocalTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException(String.format("영업 시작 시간 (%s)이 종료 시간 (%s) 보다 느릴 수 없습니다", startTime, endTime));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpeningTime that = (OpeningTime) o;
        return Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime);
    }

}
