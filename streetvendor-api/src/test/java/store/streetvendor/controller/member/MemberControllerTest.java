package store.streetvendor.controller.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import store.streetvendor.core.auth.AuthInterceptor;
import store.streetvendor.core.aws.AwsS3Service;
import store.streetvendor.service.member.MemberService;
import store.streetvendor.core.utils.dto.member.response.MemberInfoResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private AwsS3Service awsS3Service;

    @MockBean
    private AuthInterceptor authInterceptor;

    @BeforeEach
    void createAuthInterceptor() {
        BDDMockito.when(authInterceptor.preHandle(any(), any(), any())).thenReturn(true);
    }


    @Test
    void 나의_멤버_정보를_조회한다() throws Exception {
        // given
        String email = "will.seungho@gmail.com";
        Long memberId = 1000L;
        String nickName = "토끼";
        String profileUrl = "https://profile.png";

        BDDMockito.when(memberService.getMyInformation(any(), any())).thenReturn(MemberInfoResponse.builder()
            .memberId(memberId)
            .email(email)
            .nickName(nickName)
            .profileUrl(profileUrl)
            .build());

        // when & then
        mockMvc.perform(get("/api/v1/my-page")
                .header(HttpHeaders.AUTHORIZATION, "TOKEN"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.memberId").value(memberId))
            .andExpect(jsonPath("$.data.email").value(email))
            .andExpect(jsonPath("$.data.nickName").value(nickName))
            .andExpect(jsonPath("$.data.profileUrl").value(profileUrl));
    }

    @Test
    void 회원을_탈퇴한다() throws Exception {
        // given
        BDDMockito.when(memberService.signUp(any())).thenReturn(1L);

        // when & then
        mockMvc.perform(post("/api/v1/sign-out")
                .header(HttpHeaders.AUTHORIZATION, "TOKEN"))
            .andExpect(status().isOk());

    }

}
