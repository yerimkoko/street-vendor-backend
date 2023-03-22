package store.streetvendor.controller.questions;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import store.streetvendor.Auth;
import store.streetvendor.MemberId;
import store.streetvendor.core.utils.ApiResponse;
import store.streetvendor.core.utils.dto.question.request.AddQuestionRequest;
import store.streetvendor.core.utils.dto.question.response.AllQuestionResponse;
import store.streetvendor.core.utils.dto.question.response.QuestionDetailResponse;
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
    public ApiResponse<List<AllQuestionResponse>> getMyQuestions(@MemberId Long memberId,
                                                                 @RequestParam(required = false) Long cursor,
                                                                 @RequestParam(required = false, defaultValue = "5") int size) {
        return ApiResponse.success(questionService.getMyQuestion(memberId, cursor, size));
    }

    @Auth
    @Deprecated
    @ApiOperation(value = "[문의사항] 나의 문의 내역에서 상세 내용을 조회한다")
    @GetMapping("/api/v1/question/{questionId}")
    public ApiResponse<List<QuestionDetailResponse>> getQuestionDetail(@MemberId Long memberId,
                                                                       @PathVariable Long questionId,
                                                                       @RequestParam(required = false) Long cursor,
                                                                       @RequestParam(required = false, defaultValue = "5") int size) {
        return ApiResponse.success(questionService.getQuestionDetail(memberId, questionId, cursor, size));

    }

}
