package store.streetvendor.core.domain.member;

import lombok.Getter;

@Getter
public enum MemberType {
    USER("사용자"),
    BOSS("사장님")
    ;

    private final String description;

    MemberType(String description) {
        this.description = description;
    }
}
