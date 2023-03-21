package store.streetvendor.core.utils.dto.question.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.questions.Questions;
import store.streetvendor.core.domain.questions.QuestionsStatus;
import store.streetvendor.core.domain.questions.QuestionsType;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class AllQuestionResponse {

    private String type;

    private String status;

    private String title;

    private LocalDateTime createdAt;

    @Builder
    public AllQuestionResponse(String type, String status, String title, LocalDateTime createdAt) {
        this.type = type;
        this.status = status;
        this.title = title;
        this.createdAt = createdAt;
    }


    public static AllQuestionResponse of(Questions questions) {
        return AllQuestionResponse.builder()
            .type(questions.getType().name())
            .status(questions.getStatus().name())
            .title(questions.getTitle())
            .createdAt(questions.getCreatedAt())
            .build();
    }
}
