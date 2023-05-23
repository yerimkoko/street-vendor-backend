package store.streetvendor.core.domain.questions;


import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum QuestionsType {
    ORDER("주문"),
    REVIEW("리뷰"),
    MEMBER("계정"),
    ETC("기타 사항")
    ;

    private final String description;

    QuestionsType(String description) {
        this.description = description;
    }

    public static List<QuestionsType> questionsTypes() {
        return Stream.of(QuestionsType.values())
            .collect(Collectors.toList());
    }



}
