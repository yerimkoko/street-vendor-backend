package store.streetvendor.controller.member;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import store.streetvendor.config.auth.AuthInterceptor;
import store.streetvendor.service.member.MemberService;
import store.streetvendor.service.member.dto.response.MemberInfoResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private AuthInterceptor authInterceptor;

    @Test
    void 나의_멤버_정보를_조회한다() throws Exception {
        // given
        String email = "will.seungho@gmail.com";
        Long memberId = 1000L;
        String nickName = "토끼";
        String profileUrl = "https://profile.png";

        BDDMockito.when(memberService.getMyInformation(any())).thenReturn(MemberInfoResponse.builder()
            .memberId(memberId)
            .email(email)
            .nickName(nickName)
            .profileUrl(profileUrl)
            .build());

        BDDMockito.when(authInterceptor.preHandle(any(), any(), any())).thenReturn(true);

        // when & then
        mockMvc.perform(get("/api/v1/my-page")
                .header(HttpHeaders.AUTHORIZATION, "TOKEN"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.memberId").value(memberId))
            .andExpect(jsonPath("$.data.email").value(email))
            .andExpect(jsonPath("$.data.nickName").value(nickName))
            .andExpect(jsonPath("$.data.profileUrl").value(profileUrl));
    }

}
