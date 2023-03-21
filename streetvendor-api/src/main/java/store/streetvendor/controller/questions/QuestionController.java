package store.streetvendor.controller.questions;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import store.streetvendor.Auth;
import store.streetvendor.MemberId;
import store.streetvendor.core.utils.ApiResponse;
import store.streetvendor.core.utils.dto.question.request.AddQuestionRequest;
import store.streetvendor.core.utils.dto.question.response.AllQuestionResponse;
import store.streetvendor.service.question.QuestionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @Auth
    @ApiOperation(value = "[문의사항] 1:1 문의를 작성한다")
    @PostMapping("/api/v1/question")
    public ApiResponse<String> createQuestion(@MemberId Long memberId,
                                      @RequestBody AddQuestionRequest request) {
        questionService.createQuestion(memberId, request);
        return ApiResponse.OK;
    }

    @Auth
    @ApiOperation(value = "[문의사항] 내가 작성한 문의사항을 조회한다")
    @GetMapping("/api/v1/question")
    public ApiResponse<List<AllQuestionResponse>> getMyQuestions(@MemberId Long memberId) {
        return ApiResponse.success(questionService.getMyQuestion(memberId));
    }
}
