package store.streetvendor.core.domain.store.storemenu.storebadge;

import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.BaseTimeEntity;
import store.streetvendor.core.domain.store.storemenu.StoreMenu;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class StoreMenuBadge extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_menu_id", nullable = false)
    private StoreMenu storeMenu;

    @Enumerated(EnumType.STRING)
    private BadgeType badgeType;

    public StoreMenuBadge(StoreMenu storeMenu, BadgeType badgeType) {
        this.storeMenu = storeMenu;
        this.badgeType = badgeType;
    }

    public static StoreMenuBadge of(StoreMenu storeMenu, BadgeType badgeType) {
        return new StoreMenuBadge(storeMenu, badgeType);
    }

}
