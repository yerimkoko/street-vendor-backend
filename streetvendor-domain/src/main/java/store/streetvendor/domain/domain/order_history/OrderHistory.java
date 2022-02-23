package store.streetvendor.domain.domain.order_history;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class OrderHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long storeId;

    @OneToMany(mappedBy = "order_history", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderHistoryMenu> menus = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    public OrderHistory(Long memberId, Long storeId) {
         this.memberId = memberId;
         this.storeId = storeId;
    }

    public static OrderHistory newHistory(Long storeId, Long memberId) {
        return OrderHistory.builder()
            .storeId(storeId)
            .memberId(memberId)
            .build();
    }


}
