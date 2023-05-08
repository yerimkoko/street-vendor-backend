package store.streetvendor.core.utils.dto.question.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.questions.image.QuestionsImage;


@Getter
@NoArgsConstructor
public class QuestionsImageResponse {

    private String url;


    @Builder
    public QuestionsImageResponse(String url) {
        this.url = url;
    }

    public static QuestionsImageResponse of(QuestionsImage image, String baseUrl) {
        return QuestionsImageResponse.builder()
            .url(baseUrl + image.getImageUrl())
            .build();
    }
}
