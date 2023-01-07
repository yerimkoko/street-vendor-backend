package store.streetvendor.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import store.streetvendor.AuthInterceptor;
import store.streetvendor.service.member.MemberService;
import store.streetvendor.core.utils.dto.member.request.MemberSaveBossInfoRequest;
import store.streetvendor.core.utils.dto.member.response.MemberInfoResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

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

        BDDMockito.when(memberService.getMyInformation(any())).thenReturn(MemberInfoResponse.builder()
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


    @Test
    void 사장님_정보를_등록한다() throws Exception {
        // given
        String bossName = "토끼사장";
        String bossPhoneNumber = "01012345678";
        String content = objectMapper.writeValueAsString(new MemberSaveBossInfoRequest(bossName, bossPhoneNumber));

        // when & then
        mockMvc.perform(post("/api/v1/bossInfo")
            .header(HttpHeaders.AUTHORIZATION, "TOKEN")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void 사장님_정보를_확인한다() throws Exception {
        // when & then
        mockMvc.perform(get("/api/v1/boss/check")
            .header(HttpHeaders.AUTHORIZATION, "TOKEN")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void 프로필_사진을_수정한다() throws Exception {
        // when & then
        mockMvc.perform(put("/api/v1/my-page/profileUrl")
            .header(HttpHeaders.AUTHORIZATION, "TOKEN")
                .content("profileUrl")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

}
