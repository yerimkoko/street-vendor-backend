package store.streetvendor.core.domain.questions;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.BaseTimeEntity;
import store.streetvendor.core.domain.admin.Admin;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Questions extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private QuestionsType type;

    @Column
    private Long adminId;


    @OneToMany(mappedBy = "questions", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<QuestionsImage> questionsImages = new ArrayList<>();

    @Builder
    public Questions(Long memberId, String title, String content, QuestionsType type, Long adminId) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.type = type;
        this.adminId = adminId;
    }

    public static Questions newQuestions(Long memberId, String title, String content, QuestionsType type) {
        return Questions.builder()
            .memberId(memberId)
            .title(title)
            .adminId(null)
            .content(content)
            .type(type)
            .build();
    }

    public static Questions replyQuestions(Questions questions, Admin admin, String title, String content) {
        return Questions.builder()
            .memberId(questions.memberId)
            .adminId(admin.getId())
            .title(title)
            .content(content)
            .type(questions.getType())
            .build();
    }

    public void addQuestionsImage(List<QuestionsImage> images) {
        for (QuestionsImage image : images) {
            addQuestionsImage(image);
        }
    }

    public void addQuestionsImage(QuestionsImage image) {
        this.questionsImages.add(image);
    }

}
