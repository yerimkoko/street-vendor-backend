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
                questions.status.ne(QuestionsStatus.DELETED)
                )
            .orderBy(questions.id.desc())
            .limit(size)
            .fetch();
    }

    @Override
    public List<Questions> findQuestionsDetailByMemberIdAndParentId(Long memberId, Long questionId) {
        return jpaQueryFactory.selectFrom(questions)
            .where(questions.memberId.eq(memberId),
                questions.id.eq(questionId).or(questions.parentId.eq(questionId)),
                questions.status.ne(QuestionsStatus.DELETED))
            .orderBy(questions.id.desc())
            .fetch();
    }

    @Override
    public Questions findByQuestionId(Long questionId, Long memberId) {
        return jpaQueryFactory.selectFrom(questions)
            .where(questions.id.eq(questionId),
                questions.memberId.eq(memberId),
                questions.status.ne(QuestionsStatus.DELETED))
            .fetchOne();
    }

    @Override
    public Questions findByQuestionIdAndMemberId(Long questionId, Long memberId) {
        return jpaQueryFactory.selectFrom(questions)
            .where(questions.id.eq(questionId),
                questions.memberId.eq(memberId))
            .fetchOne();
    }

    private BooleanExpression existedCursor(Long cursor) {
        if (cursor == null) {
            return null;
        }
        return questions.id.lt(cursor);
    }

}
