package store.streetvendor.core.utils.dto.question.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.questions.Questions;
import store.streetvendor.core.domain.questions.QuestionsType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class AddQuestionRequest {

    @NotNull
    private QuestionsType type;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @Size(max = 5)
    private List<QuestionsImageRequest> images;

    public AddQuestionRequest(QuestionsType type, String title, String content, List<QuestionsImageRequest> images) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.images = images;
    }

    public Questions toEntity(Long memberId) {
        Questions questions = Questions.newQuestions(memberId, title, content, type);
        questions.addQuestionImages(images.stream()
            .map(image -> image.toEntity(questions))
            .collect(Collectors.toList()));
        return questions;
    }

}
