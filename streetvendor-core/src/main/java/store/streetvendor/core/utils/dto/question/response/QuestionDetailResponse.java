package store.streetvendor.core.utils.dto.question.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.questions.Questions;
import store.streetvendor.core.domain.questions.image.QuestionsImage;

import java.util.List;

@Getter
@NoArgsConstructor
public class QuestionDetailResponse {

    private String type;

    private String title;

    private String content;

    private List<QuestionsImage> images;

    private String writtenBy;

    @Builder
    public QuestionDetailResponse(String type, String title, String content, List<QuestionsImage> images, String writtenBy) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.images = images;
        this.writtenBy = writtenBy;
    }

    public static QuestionDetailResponse of(Questions questions) {
        return QuestionDetailResponse.builder()
            .type(questions.getType().getDescription())
            .title(questions.getTitle())
            .content(questions.getContent())
            .images(questions.getQuestionsImages())
            .writtenBy(validateWrittenBy(questions))
            .build();
    }

    public static String validateWrittenBy(Questions questions) {
        if (questions.getAdminId() == null) {
            return "나";
        }
        return "관리자";

    }
}
