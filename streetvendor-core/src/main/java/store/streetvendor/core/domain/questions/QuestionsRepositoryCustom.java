package store.streetvendor.core.domain.questions;

import java.util.List;

public interface QuestionsRepositoryCustom {

    List<Questions> findQuestionsByMemberId(Long memberId, Long cursor, int size);

    List<Questions> findQuestionsDetailByMemberIdAndParentId(Long memberId, Long questionId);

    Questions findByQuestionId(Long questionId);

    Questions findByQuestionIdAndMemberId(Long questionId, Long memberId);



}
