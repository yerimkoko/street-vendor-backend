package store.streetvendor.core.domain.store.storemenu;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.BaseTimeEntity;
import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.domain.store.menu.Menu;
import store.streetvendor.core.domain.store.storemenu.storebadge.BadgeType;
import store.streetvendor.core.domain.store.storemenu.storebadge.StoreMenuBadge;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreMenu extends BaseTimeEntity {

    private static final int ORDER_COUNT = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column
    private long orderMenuCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @OneToMany(mappedBy = "store_menu_badge_id", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<StoreMenuBadge> storeMenuBadges = new ArrayList<>();

    @Builder
    public StoreMenu(Store store, Member member, Menu menu, long orderMenuCount) {
        this.store = store;
        this.member = member;
        this.orderMenuCount = orderMenuCount;
        this.menu = menu;
    }

    public static StoreMenu newStoreMenu(Store store, Member member, Menu menu) {
        return StoreMenu.builder()
            .store(store)
            .member(member)
            .orderMenuCount(1)
            .menu(menu)
            .build();
    }

    public void addOrderMenuCount() {
        this.orderMenuCount += 1;
    }

    private void addBestBadge() {
        this.storeMenuBadges.add(StoreMenuBadge.of(this, BadgeType.BEST));
    }

}
