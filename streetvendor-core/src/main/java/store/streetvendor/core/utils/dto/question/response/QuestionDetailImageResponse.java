package store.streetvendor.core.utils.dto.question.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.questions.image.QuestionsImage;

@NoArgsConstructor
@Getter
public class QuestionDetailImageResponse {

    private String imageUrl;

    public QuestionDetailImageResponse(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static QuestionDetailImageResponse of(QuestionsImage image) {
        return new QuestionDetailImageResponse(image.getImageUrl());
    }
}

