package store.streetvendor.service.store.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.store.Grade;

@NoArgsConstructor
@Getter
public class AddStoreReviewRequest {

    private Grade grade;

    private String comment;

    @Builder
    public AddStoreReviewRequest(Grade grade, String comment) {
        this.grade = grade;
        this.comment = comment;
    }

}
