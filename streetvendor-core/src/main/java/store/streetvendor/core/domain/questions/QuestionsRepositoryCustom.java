package store.streetvendor.core.domain.questions;

import java.util.List;

public interface QuestionsRepositoryCustom {

    List<Questions> findQuestionsByMemberId(Long memberId);

}
