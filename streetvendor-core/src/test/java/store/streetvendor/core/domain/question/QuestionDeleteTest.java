package store.streetvendor.core.domain.question;

import org.junit.jupiter.api.Test;
import store.streetvendor.core.domain.questions.Questions;
import store.streetvendor.core.domain.questions.QuestionsStatus;
import store.streetvendor.core.domain.questions.QuestionsType;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class QuestionDeleteTest {

    @Test
    void 문의사항이_지워진다() {
        // given
        Questions questions = Questions.newQuestions(1L, "title", "content", QuestionsType.ETC);

        // when
        questions.delete();

        // then
        assertThat(questions.getStatus()).isEqualTo(QuestionsStatus.DELETED);
    }
}
