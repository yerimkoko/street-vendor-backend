package store.streetvendor.core.domain.questions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum QuestionsStatus {

    REPLY_WAITING("답변 대기"),
    REPLY_COMPLETED("답변 완료")
    ;

    private final String description;

    public String getDescription() {
        return this.description;
    }
}
