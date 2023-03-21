package store.streetvendor.core.domain.questions;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static store.streetvendor.core.domain.questions.QQuestions.questions;

@RequiredArgsConstructor
public class QuestionsRepositoryCustomImpl implements QuestionsRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Questions> findQuestionsByMemberId(Long memberId) {
        return jpaQueryFactory.selectFrom(questions)
            .where(questions.memberId.eq(memberId))
            .fetch();
    }
}
