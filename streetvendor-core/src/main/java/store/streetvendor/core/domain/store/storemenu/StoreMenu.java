package store.streetvendor.core.domain.store.storemenu;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.BaseTimeEntity;
import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.domain.store.menu.Menu;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class StoreMenu extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private long orderMenuCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Builder
    public StoreMenu(Store store, Member member, long orderMenuCount, Menu menu) {
        this.store = store;
        this.member = member;
        this.orderMenuCount = orderMenuCount;
        this.menu = menu;
    }

    public static StoreMenu newStoreMenu(Store store, Member member, Menu menu) {
        return StoreMenu.builder()
            .store(store)
            .member(member)
            .menu(menu)
            .orderMenuCount(1)
            .build();
    }

}
