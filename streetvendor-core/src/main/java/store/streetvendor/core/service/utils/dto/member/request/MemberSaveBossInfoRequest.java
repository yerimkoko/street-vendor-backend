package store.streetvendor.core.service.utils.dto.member.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class MemberSaveBossInfoRequest {

    @NotBlank(message = "이름을 입력해주세요.")
    private String bossName;

    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    @Size(min = 10, max = 11, message = "10 ~ 11자 이내로 입력해주세요.")
    private String bossPhoneNumber;

    public MemberSaveBossInfoRequest(String bossName, String bossPhoneNumber) {
        this.bossName = bossName;
        this.bossPhoneNumber = bossPhoneNumber;
    }

}
