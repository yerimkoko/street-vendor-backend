package store.streetvendor.core.domain.questions;

import java.util.List;

public interface QuestionsRepositoryCustom {

    List<Questions> findQuestionsByMemberId(Long memberId, Long cursor, int size);

    List<Questions> findQuestionsDetailByMemberId(Long memberId, Long questionId, Long cursor, int size);

    Questions findByQuestionId(Long questionId);

    Questions findByQuestionIdAndMemberId(Long questionId, Long memberId);

}
