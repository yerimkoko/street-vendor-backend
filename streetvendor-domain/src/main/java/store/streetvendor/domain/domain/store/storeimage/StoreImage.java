package store.streetvendor.domain.domain.store.storeimage;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.BaseTimeEntity;
import store.streetvendor.domain.domain.store.Store;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class StoreImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false)
    private Boolean isThumbNail;

    @Column(nullable = false)
    private String pictureUrl;

    @Builder
    private StoreImage(Store store, String pictureUrl, Boolean isThumbNail) {
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
