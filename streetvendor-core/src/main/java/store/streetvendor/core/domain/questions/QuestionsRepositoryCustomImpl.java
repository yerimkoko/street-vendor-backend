package store.streetvendor.core.domain.questions;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static store.streetvendor.core.domain.questions.QQuestions.questions;

@RequiredArgsConstructor
public class QuestionsRepositoryCustomImpl implements QuestionsRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Questions> findQuestionsByMemberId(Long memberId, Long cursor, int size) {
        return jpaQueryFactory.selectFrom(questions)
            .where(questions.memberId.eq(memberId),
                existedCursor(cursor),
                questions.adminId.isNull())
            .orderBy(questions.id.desc())
            .limit(size)
            .fetch();
    }

    @Override
    public List<Questions> findQuestionsDetailByMemberId(Long memberId, Long questionId, Long cursor, int size) {
        return jpaQueryFactory.selectFrom(questions)
            .where(questions.memberId.eq(memberId),
                questions.id.eq(questionId),
                existedCursor(cursor))
            .orderBy(questions.id.desc())
            .limit(size)
            .fetch();
    }

    @Override
    public Questions findByQuestionId(Long questionId) {
        return jpaQueryFactory.selectFrom(questions)
            .where(questions.id.eq(questionId))
            .fetchOne();
    }

    private BooleanExpression existedCursor(Long cursor) {
        if (cursor == null) {
            return null;
        }
        return questions.id.lt(cursor);
    }

}
