package store.streetvendor.core.domain.questions;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.BaseTimeEntity;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionsImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "questions_id", nullable = false)
    private Questions questions;

    private String imageUrl;

    @Builder
    public QuestionsImage(Questions questions, String imageUrl) {
        this.questions = questions;
        this.imageUrl = imageUrl;
    }

}
