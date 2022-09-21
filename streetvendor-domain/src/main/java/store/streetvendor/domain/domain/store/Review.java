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
public class Review extends BaseTimeEntity {

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
    public Review(Long id, Store store, Long memberId, String comment, Grade grade) {
        this.id = id;
        this.store = store;
        this.memberId = memberId;
        this.comment = comment;
        this.grade = grade;
    }

    public static Review of(Store store, Long memberId, Grade grade, String comment) {
        return Review.builder()
            .comment(comment)
            .memberId(memberId)
            .store(store)
            .grade(grade)
            .build();
    }

    public static long getGradeValue(Grade grade) {
        return grade.getValue();
    }

}
