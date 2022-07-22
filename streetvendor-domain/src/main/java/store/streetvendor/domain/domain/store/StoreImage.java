package store.streetvendor.domain.domain.store;

import lombok.*;
import store.streetvendor.domain.domain.BaseTimeEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
public class StoreImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false)
    private boolean isThumbNail;

    private String pictureUrl;

    @Builder(access = AccessLevel.PRIVATE)
    private StoreImage(Store store, String pictureUrl, boolean isThumbNail) {
        this.store = store;
        this.pictureUrl = pictureUrl;
        this.isThumbNail = isThumbNail;
    }

    public static StoreImage of(Store store, boolean isThumbNail, String pictureUrl) {
        return StoreImage.builder()
            .store(store)
            .isThumbNail(isThumbNail)
            .pictureUrl(pictureUrl)
            .build();
    }

}
