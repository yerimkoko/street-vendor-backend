package store.streetvendor.domain.domain.store;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Evaluation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    private Long memberId;

    private String comment;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Builder
    public Evaluation(Long id, Store store, Long memberId, String comment, Grade grade) {
        this.id = id;
        this.store = store;
        this.memberId = memberId;
        this.comment = comment;
        this.grade = grade;
    }

    public static Evaluation of(Store store, Grade grade, String comment) {
        return Evaluation.builder()
            .comment(comment)
            .memberId(store.getMemberId())
            .store(store)
            .grade(grade)
            .build();
    }

}
