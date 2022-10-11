package store.streetvendor.domain.domain.store.star;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.BaseTimeEntity;
import store.streetvendor.domain.domain.store.Store;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Star extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    private StarStatus status;

    @Builder
    public Star(Store store, Long memberId) {
        this.store = store;
        this.memberId = memberId;
    }


    public static Star of(Store store, Long memberId) {
        return Star.builder()
            .store(store)
            .memberId(memberId)
            .build();
    }

    public void delete() {
        this.status = StarStatus.DELETE;
    }
}
