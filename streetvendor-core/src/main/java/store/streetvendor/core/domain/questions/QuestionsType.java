package store.streetvendor.core.domain.questions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum QuestionsType {
    ORDER("주문"),
    REVIEW("리뷰"),
    MEMBER("계정"),
    ETC("기타 사항")
    ;

    private final String description;

    public String getDescription() {
        return this.description;
    }


}
