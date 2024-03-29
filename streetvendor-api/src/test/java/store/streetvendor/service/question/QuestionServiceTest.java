package store.streetvendor.service.question;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.MemberFixture;
import store.streetvendor.core.aws.response.ImageUrlResponse;
import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.domain.member.MemberRepository;
import store.streetvendor.core.domain.questions.Questions;
import store.streetvendor.core.domain.questions.QuestionsRepository;
import store.streetvendor.core.domain.questions.QuestionsStatus;
import store.streetvendor.core.domain.questions.QuestionsType;
import store.streetvendor.core.domain.questions.image.QuestionImageRepository;
import store.streetvendor.core.domain.questions.image.QuestionsImage;
import store.streetvendor.core.utils.dto.question.request.AddQuestionRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class QuestionServiceTest extends MemberFixture {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private QuestionImageRepository questionImageRepository;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void cleanUp() {
        questionImageRepository.deleteAll();
        questionsRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void 문의사항을_추가한다() {
        // given
        QuestionsType type = QuestionsType.ETC;
        String title = "치킨이 먹고 싶은데요";
        String content = "어떤 치킨이냐면요.";
        ImageUrlResponse response = ImageUrlResponse.of("imageUrl");
        AddQuestionRequest request = new AddQuestionRequest(type, title, content, List.of(response));

        // when
        questionService.createQuestion(getMember().getId(), request);

        // then
        List<Questions> questions = questionsRepository.findAll();
        List<QuestionsImage> questionsImages = questionImageRepository.findAll();

        assertThat(questions).hasSize(1);
        assertThat(questions.get(0).getType()).isEqualTo(type);
        assertThat(questions.get(0).getTitle()).isEqualTo(title);
        assertThat(questions.get(0).getContent()).isEqualTo(content);

        assertThat(questionsImages.get(0).getImageUrl()).isEqualTo(response.getImageUrl());

    }

    @Test
    void 문의사항을_지운다() {
        // given
        Member member = getMember();
        Questions questions = Questions.newQuestions(member.getId(), "title", "content", QuestionsType.ETC);
        questionsRepository.save(questions);

        // when
        questionService.deleteQuestion(questions.getId(), member.getId());

        // then
        List<Questions> questionList = questionsRepository.findAll();
        assertThat(questionList.get(0).getStatus()).isEqualTo(QuestionsStatus.DELETED);
    }


    private Member getMember() {
        return memberRepository.save(member());
    }
}
