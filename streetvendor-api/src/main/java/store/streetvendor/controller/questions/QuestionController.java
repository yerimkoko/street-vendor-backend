package store.streetvendor.controller.questions;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import store.streetvendor.core.auth.Auth;
import store.streetvendor.core.auth.MemberId;
import store.streetvendor.core.aws.response.ImageUrlResponse;
import store.streetvendor.core.exception.ConflictException;
import store.streetvendor.core.exception.InvalidException;
import store.streetvendor.core.mapper.question.QuestionUtils;
import store.streetvendor.core.mapper.question.dto.response.QuestionMapperResponse;
import store.streetvendor.core.utils.ApiResponse;
import store.streetvendor.core.utils.dto.question.request.AddQuestionRequest;
import store.streetvendor.core.utils.dto.question.response.AllQuestionResponse;
import store.streetvendor.core.utils.dto.question.response.QuestionDetailResponse;
import store.streetvendor.service.question.QuestionService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final static int MAX_QUESTION_IMAGES = 5;

    @Value("${cloud.aws.s3.baseUrl}")
    private String baseUrl;

    private final QuestionService questionService;

    @Auth
    @ApiOperation(value = "[공통] [문의사항] 1:1 문의를 작성한다")
    @PostMapping("/api/v1/question")
    public ApiResponse<String> createQuestion(@MemberId Long memberId,
                                              @RequestBody @Valid AddQuestionRequest request) {
        questionService.createQuestion(memberId, request);

        return ApiResponse.OK;
    }

    @Auth
    @ApiOperation(value = "[공통] [문의사항] 문의사항에 사진을 등록한다")
    @PostMapping("/api/v1/question/images")
    public ApiResponse<List<ImageUrlResponse>> createQuestionImages(@RequestPart List<MultipartFile> imageFiles) {
        if (imageFiles.isEmpty()) {
            throw new InvalidException("해당하는 이미지 파일이 존재하지 않습니다.");
        }
        if (imageFiles.size() > MAX_QUESTION_IMAGES) {
            throw new ConflictException(String.format("최대 [%s]장 까지 가능합니다.", MAX_QUESTION_IMAGES));
        }
        return ApiResponse.success(questionService.addQuestionImages(imageFiles));
    }


    @Auth
    @ApiOperation(value = "[공통] [문의사항] 내가 작성한 문의사항을 조회한다")
    @GetMapping("/api/v1/question")
    public ApiResponse<List<AllQuestionResponse>> getMyQuestions(@MemberId Long memberId,
                                                                 @RequestParam(required = false) Long cursor,
                                                                 @RequestParam(required = false, defaultValue = "5") int size) {
        return ApiResponse.success(questionService.getMyQuestion(memberId, cursor, size, baseUrl));
    }

    @Auth
    @ApiOperation(value = "[공통] [문의사항] 나의 문의 내역에서 상세 내용을 조회한다")
    @GetMapping("/api/v1/question/{questionId}")
    public ApiResponse<List<QuestionDetailResponse>> getQuestionDetail(@MemberId Long memberId,
                                                                       @PathVariable Long questionId) {
        return ApiResponse.success(questionService.getQuestionDetail(memberId, questionId, baseUrl));

    }

    @ApiOperation(value = "[공통] [문의사항] 타입을 조회한다")
    @GetMapping("/api/v1/question/question-type")
    public ApiResponse<List<QuestionMapperResponse>> getQuestionType() {
        return ApiResponse.success(QuestionUtils.getQuestionTypes());
    }


    @ApiOperation(value = "[공통] [문의사항] 나의 문의 내역 삭제하기")
    @DeleteMapping("/api/v1/question/{questionId}")
    public ApiResponse<String> deleteQuestion(@PathVariable Long questionId,
                                              @MemberId Long memberId) {
        questionService.deleteQuestion(questionId, memberId);
        return ApiResponse.OK;
    }
}
