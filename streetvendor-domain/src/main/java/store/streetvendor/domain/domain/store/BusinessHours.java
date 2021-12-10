package store.streetvendor.domain.domain.store;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@Entity
public class BusinessHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Embedded
    private OpeningTime openingTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Days days;

    private BusinessHours(Store store, Days days, LocalTime startTime, LocalTime endTime) {
        this.store = store;
        this.days = days;
        this.openingTime = OpeningTime.of(startTime, endTime);
    }

    public static BusinessHours of(Store store, Days days, LocalTime startTime, LocalTime endTime) {
        return new BusinessHours(store, days, startTime, endTime);
    }

}
