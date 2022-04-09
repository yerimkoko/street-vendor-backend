package store.streetvendor.service.member.auth.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthRequest {

    @NotBlank
    private String requestToken;

}
