package store.streetvendor.service.member.dto.request;

import lombok.Getter;

@Getter
public class MemberSaveBossInfoRequest {

    private String bossName;

    private String bossPhoneNumber;

    public MemberSaveBossInfoRequest(String bossName, String bossPhoneNumber) {
        this.bossName = bossName;
        this.bossPhoneNumber = bossPhoneNumber;
    }


}
