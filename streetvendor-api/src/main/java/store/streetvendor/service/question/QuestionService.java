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
    public void createQuestion(Long memberId, AddQuestionRequest request, List<MultipartFile> imageFiles) {

        Questions question = request.toEntity(memberId);

        if (!imageFiles.isEmpty()) {
            addQuestionImages(question, imageFiles);
        }

        questionsRepository.save(request.toEntity(memberId));
    }

    private void addQuestionImages(Questions question, List<MultipartFile> imageFiles) {

        List<FileUploadRequest> uploadRequest = imageFiles.stream()
            .map(multipartFile -> ImageFileUploadRequest.of(multipartFile, ImageFileType.QUESTION_IMAGE))
            .collect(Collectors.toList());

        List<QuestionsImage> questions = s3Service.uploadImageFiles(uploadRequest).stream()
            .map(imageUrlResponse -> QuestionsImage.newImage(question, imageUrlResponse.getImageUrl()))
            .collect(Collectors.toList());

        question.addQuestionImages(questions);

    }

    @Transactional(readOnly = true)
    public List<AllQuestionResponse> getMyQuestion(Long memberId, Long cursor, int size) {
        return questionsRepository.findQuestionsByMemberId(memberId, cursor, size).stream()
            .map(AllQuestionResponse::of)
            .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public List<QuestionDetailResponse> getQuestionDetail(Long memberId, Long questionId, Long cursor, int size) {
        return questionsRepository.findQuestionsDetailByMemberId(memberId, questionId, cursor, size).stream()
            .map(QuestionDetailResponse::of)
            .collect(Collectors.toList());
    }


}
