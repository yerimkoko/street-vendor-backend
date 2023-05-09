package store.streetvendor.service.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import store.streetvendor.core.aws.AwsS3Service;
import store.streetvendor.core.aws.ImageFileType;
import store.streetvendor.core.aws.request.FileUploadRequest;
import store.streetvendor.core.aws.request.ImageFileUploadRequest;
import store.streetvendor.core.domain.questions.Questions;
import store.streetvendor.core.domain.questions.QuestionsRepository;
import store.streetvendor.core.domain.questions.image.QuestionsImage;
import store.streetvendor.core.exception.NotFoundException;
import store.streetvendor.core.utils.dto.question.request.AddQuestionRequest;
import store.streetvendor.core.utils.dto.question.response.AllQuestionResponse;
import store.streetvendor.core.utils.dto.question.response.QuestionDetailResponse;
import store.streetvendor.core.utils.dto.question.response.QuestionsImageResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionsRepository questionsRepository;

    private final AwsS3Service s3Service;

    @Transactional
    public Long createQuestion(Long memberId, AddQuestionRequest request) {
        Questions questions = questionsRepository.save(request.toEntity(memberId));
        return questions.getId();
    }

    @Transactional
    public List<QuestionsImageResponse> addQuestionImages(Long memberId, Long questionId, List<MultipartFile> imageFiles, String baseUrl) {

        Questions question = questionsRepository.findByQuestionIdAndMemberId(questionId, memberId);
        if (question == null) {
            throw new NotFoundException(String.format("[%s]에 해당하는 문의사항은 존재하지 않습니다.", questionId));
        }

        List<FileUploadRequest> uploadRequest = imageFiles.stream()
            .map(multipartFile -> ImageFileUploadRequest.of(multipartFile, ImageFileType.QUESTION_IMAGE))
            .collect(Collectors.toList());

        List<QuestionsImage> questionImages = s3Service.uploadImageFiles(uploadRequest).stream()
            .map(imageUrlResponse -> QuestionsImage.newImage(question, imageUrlResponse.getImageUrl()))
            .collect(Collectors.toList());

        question.addQuestionImages(questionImages);

        return questionImages.stream()
            .map(image -> QuestionsImageResponse.of(image, baseUrl))
            .collect(Collectors.toList());

    }


    @Transactional(readOnly = true)
    public List<AllQuestionResponse> getMyQuestion(Long memberId, Long cursor, int size, String baseUrl) {
        return questionsRepository.findQuestionsByMemberId(memberId, cursor, size).stream()
            .map(question -> AllQuestionResponse.of(question, baseUrl))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<QuestionDetailResponse> getQuestionDetail(Long memberId, Long questionId, Long cursor, int size) {
        return questionsRepository.findQuestionsDetailByMemberId(memberId, questionId, cursor, size).stream()
            .map(QuestionDetailResponse::of)
            .collect(Collectors.toList());
    }


}
