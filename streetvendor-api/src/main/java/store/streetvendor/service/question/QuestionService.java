package store.streetvendor.service.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.core.domain.questions.QuestionsRepository;
import store.streetvendor.core.utils.dto.question.request.AddQuestionRequest;
import store.streetvendor.core.utils.dto.question.response.AllQuestionResponse;
import store.streetvendor.core.utils.dto.question.response.QuestionDetailResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionsRepository questionsRepository;

    @Transactional
    public void createQuestion(Long memberId, AddQuestionRequest request) {
        questionsRepository.save(request.toEntity(memberId));
    }

    @Transactional(readOnly = true)
    public List<AllQuestionResponse> getMyQuestion(Long memberId, Long cursor, int size) {
        return questionsRepository.findQuestionsByMemberId(memberId, cursor, size).stream()
            .map(AllQuestionResponse::of)
            .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public List<QuestionDetailResponse> getQuestionDetail(Long memberId, Long questionId, Long cursor, int size) {
        return questionsRepository.findQuestionsDetailByMemberId(memberId, questionId, cursor, size).stream()
            .map(QuestionDetailResponse::of)
            .collect(Collectors.toList());
    }


}
