package store.streetvendor.core.utils.dto.question.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.questions.Questions;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class AllQuestionResponse {

    private Long questionId;

    private String type;

    private String status;

    private String title;

    private LocalDateTime createdAt;

    @Builder
    public AllQuestionResponse(Long questionId, String type, String status, String title, LocalDateTime createdAt) {
        this.questionId = questionId;
        this.type = type;
        this.status = status;
        this.title = title;
        this.createdAt = createdAt;
    }


    public static AllQuestionResponse of(Questions questions) {
        return AllQuestionResponse.builder()
            .questionId(questions.getId())
            .type(questions.getType().getDescription())
            .status(questions.getStatus().getDescription())
            .title(questions.getTitle())
            .createdAt(questions.getCreatedAt())
            .build();
    }
}