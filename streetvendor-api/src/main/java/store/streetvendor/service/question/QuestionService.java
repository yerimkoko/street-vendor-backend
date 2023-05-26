package store.streetvendor.service.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import store.streetvendor.core.aws.AwsS3Service;
import store.streetvendor.core.aws.ImageFileType;
import store.streetvendor.core.aws.request.FileUploadRequest;
import store.streetvendor.core.aws.request.ImageFileUploadRequest;
import store.streetvendor.core.aws.response.ImageUrlResponse;
import store.streetvendor.core.domain.questions.Questions;
import store.streetvendor.core.domain.questions.QuestionsRepository;
import store.streetvendor.core.domain.questions.image.QuestionsImage;
import store.streetvendor.core.utils.dto.question.request.AddQuestionRequest;
import store.streetvendor.core.utils.dto.question.response.AllQuestionResponse;
import store.streetvendor.core.utils.dto.question.response.QuestionDetailResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionsRepository questionsRepository;

    private final AwsS3Service s3Service;

    @Transactional
    public void createQuestion(Long memberId, AddQuestionRequest request) {
        Questions questions = request.toEntity(memberId);
        saveImageUrl(questions, request.getQuestionsImages());
        questionsRepository.save(questions);

    }

    private void saveImageUrl(Questions questions, List<ImageUrlResponse> urlResponses) {
        if (CollectionUtils.isEmpty(urlResponses)) {
            return;
        }
        List<QuestionsImage> questionsImageList = urlResponses.stream()
            .map(image -> QuestionsImage.newImage(questions, image.getImageUrl()))
            .collect(Collectors.toList());
        questions.addQuestionImages(questionsImageList);
    }


    public List<ImageUrlResponse> addQuestionImages(List<MultipartFile> imageFiles) {
        List<FileUploadRequest> uploadRequest = imageFiles.stream()
            .map(multipartFile -> ImageFileUploadRequest.of(multipartFile, ImageFileType.QUESTION_IMAGE))
            .collect(Collectors.toList());
        return s3Service.uploadImageFiles(uploadRequest);
    }


    @Transactional(readOnly = true)
    public List<AllQuestionResponse> getMyQuestion(Long memberId, Long cursor, int size, String baseUrl) {
        return questionsRepository.findQuestionsByMemberId(memberId, cursor, size).stream()
            .map(question -> AllQuestionResponse.of(question, baseUrl))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<QuestionDetailResponse> getQuestionDetail(Long memberId, Long questionId, String baseUrl) {
        return questionsRepository.findQuestionsDetailByMemberIdAndParentId(memberId, questionId).stream()
            .map(question -> QuestionDetailResponse.of(question, baseUrl))
            .collect(Collectors.toList());
    }

    /**
     * 통합 테스트 코드에 문제가 있음
     * @param questionId
     * @param memberId
     */
    @Transactional
    public void deleteQuestion(Long questionId, Long memberId) {
        List<Questions> questions = questionsRepository.findQuestionsDetailByMemberIdAndParentId(memberId, questionId);
        for (Questions question : questions) {
            question.delete();
        }
        // questionsRepository.saveAll(questions);
    }
}
