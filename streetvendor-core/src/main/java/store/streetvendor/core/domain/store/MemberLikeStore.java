package store.streetvendor.core.domain.store;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.BaseTimeEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
public class MemberLikeStore extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(nullable = false)
    private Long memberId;

    @Builder
    public MemberLikeStore(Store store, Long memberId) {
        this.store = store;
        this.memberId = memberId;
    }

    public static MemberLikeStore newInstance(@NotNull Long memberId, @NotNull Store store) {
        return MemberLikeStore.builder()
            .memberId(memberId)
            .store(store)
            .build();
    }

}
