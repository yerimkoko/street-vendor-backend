package store.streetvendor.core.utils.dto.question.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.questions.Questions;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class QuestionDetailResponse {

    private static final String ME = "나";

    private static final String ADMIN = "관리자";


    private String type;

    private String title;

    private String content;

    private List<QuestionDetailImageResponse> images;

    private String writtenBy;

    @Builder
    public QuestionDetailResponse(String type, String title, String content, List<QuestionDetailImageResponse> images, String writtenBy) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.images = images;
        this.writtenBy = writtenBy;
    }

    public static QuestionDetailResponse of(Questions questions, String baseUrl) {
        return QuestionDetailResponse.builder()
            .type(questions.getType().getDescription())
            .title(questions.getTitle())
            .content(questions.getContent())
            .images(questions.getQuestionsImages().stream()
                .map(question -> QuestionDetailImageResponse.of(question, baseUrl))
                .collect(Collectors.toList()))
            .writtenBy(validateWrittenBy(questions))
            .build();
    }

    public static String validateWrittenBy(Questions questions) {
        if (questions.getAdminId() == null) {
            return ME;
        }
        return ADMIN;

    }
}
