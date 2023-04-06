package store.streetvendor.core.utils.dto.question.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.questions.Questions;
import store.streetvendor.core.domain.questions.image.QuestionsImage;

@NoArgsConstructor
@Getter
public class QuestionsImageRequest {

    private String url;

    public QuestionsImageRequest(String url) {
        this.url = url;
    }


    public QuestionsImage toEntity(Questions questions) {
        return QuestionsImage.newImage(questions, url);
    }

}
