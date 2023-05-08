package store.streetvendor.core.domain.questions.image;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.BaseTimeEntity;
import store.streetvendor.core.domain.questions.Questions;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionsImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questions_id", nullable = false)
    private Questions questions;

    @Column
    private String imageUrl;

    @Builder
    public QuestionsImage(Questions questions, String imageUrl) {
        this.questions = questions;
        this.imageUrl = imageUrl;
    }

    public static QuestionsImage newImage(Questions questions, String imagesUrl) {
        return QuestionsImage.builder()
            .imageUrl(imagesUrl)
            .questions(questions)
            .build();
    }

}
