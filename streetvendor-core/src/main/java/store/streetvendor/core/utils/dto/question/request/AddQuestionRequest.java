package store.streetvendor.core.utils.dto.question.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import store.streetvendor.core.aws.response.ImageUrlResponse;
import store.streetvendor.core.domain.questions.Questions;
import store.streetvendor.core.domain.questions.QuestionsType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Getter
public class AddQuestionRequest {

    @NotNull
    private QuestionsType type;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @Nullable
    private List<ImageUrlResponse> questionsImages;

    public AddQuestionRequest(QuestionsType type, String title, String content, @Nullable List<ImageUrlResponse> questionsImages) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.questionsImages = questionsImages;
    }

    public Questions toEntity(Long memberId) {
        return Questions.newQuestions(memberId, title, content, type);
    }

}
