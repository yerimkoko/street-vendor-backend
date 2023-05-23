package store.streetvendor.core.mapper.question;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.questions.QuestionsType;
import store.streetvendor.core.mapper.question.dto.response.QuestionMapperResponse;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionUtils {

    public static List<QuestionMapperResponse> getQuestionTypes() {
        return QuestionsType.questionsTypes().stream()
            .map(QuestionMapperResponse::of)
            .collect(Collectors.toList());
    }
}
