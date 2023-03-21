package store.streetvendor.controller.question;

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
import store.streetvendor.controller.questions.QuestionController;
import store.streetvendor.core.domain.questions.QuestionsType;
import store.streetvendor.core.utils.dto.question.request.AddQuestionRequest;
import store.streetvendor.service.question.QuestionService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuestionController.class)
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthInterceptor authInterceptor;

    @BeforeEach
    void createAuthInterceptor() {
        BDDMockito.when(authInterceptor.preHandle(any(), any(), any()))
            .thenReturn(true);
    }


    @Test
    void 문의사항을_작성한다() throws Exception {
        // given
        QuestionsType type = QuestionsType.OTHERS;
        String title = "제목입니다.";
        String content = "내용 입니다.";
        AddQuestionRequest request = new AddQuestionRequest(type, title, content, Collections.emptyList());

        // when & then
        mockMvc.perform(post("/api/v1/question")
            .header(HttpHeaders.AUTHORIZATION, "TOKEN")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

    }


}
