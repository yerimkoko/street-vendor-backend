package store.streetvendor.core.mapper.question.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.questions.QuestionsType;

@NoArgsConstructor
@Getter
public class QuestionMapperResponse {

    private String key;

    private String value;

    @Builder
    public QuestionMapperResponse(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static QuestionMapperResponse of(QuestionsType type){
        return QuestionMapperResponse.builder()
            .key(type.name())
            .value(type.getDescription())
            .build();
    }
}
