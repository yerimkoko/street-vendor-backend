package store.streetvendor.core.domain.store;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OpeningTimeTest {

    @Test
    void 오픈_시간이_폐점시간_보다_느린경우_에러가_발생한다() {
        // given
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(8, 59);

        // when & then
        assertThatThrownBy(() -> OpeningTime.of(startTime, endTime)).isInstanceOf(IllegalArgumentException.class);
    }

}
