package store.streetvendor.service.store;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import store.streetvendor.core.aws.AwsS3Service;
import store.streetvendor.core.aws.ImageFileType;
import store.streetvendor.core.aws.request.FileUploadRequest;
import store.streetvendor.core.aws.request.ImageFileUploadRequest;
import store.streetvendor.core.aws.response.ImageUrlResponse;
import store.streetvendor.core.domain.member.MemberRepository;
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.domain.store.StoreRepository;
import store.streetvendor.core.domain.store.StoreSalesStatus;
import store.streetvendor.core.exception.DuplicatedException;
import store.streetvendor.core.utils.dto.store.request.AddNewStoreRequest;
import store.streetvendor.core.utils.dto.store.request.StoreUpdateRequest;
import store.streetvendor.core.utils.service.MemberServiceUtils;
import store.streetvendor.core.utils.service.StoreServiceUtils;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BossStoreService {

    private final StoreRepository storeRepository;

    private final MemberRepository memberRepository;

    private final AwsS3Service awsS3Service;

    @Transactional
    public void addNewStore(AddNewStoreRequest request, Long bossId) {
        MemberServiceUtils.findBossByBossId(memberRepository, bossId);
        storeRepository.save(request.toEntity(bossId));
    }

    @Transactional
    public List<ImageUrlResponse> addStoreImage(List<MultipartFile> storeImages) {
        List<FileUploadRequest> fileUploadRequests = storeImages.stream()
            .map(imageFile -> ImageFileUploadRequest.of(imageFile, ImageFileType.STORE_IMAGE))
            .collect(Collectors.toList());

        return awsS3Service.uploadImageFiles(fileUploadRequests);

    }

    @Transactional
    public void updateMyStore(Long memberId, Long storeId, StoreUpdateRequest request) {
        Store store = StoreServiceUtils.findStoreByStoreIdAndMemberId(storeRepository, storeId, memberId);
        store.updateStoreInfo(request.getName(), request.getDescription(), request.getLocation(), request.getCategory(), request.getBankInfo());
        store.updateMenus(request.toMenus(store));
        store.updatePayments(request.getPaymentMethods());
        store.updateBusinessDaysInfo(request.toBusinessHours(store));
    }

    @Transactional
    public void deleteMyStore(Long memberId, Long storeId) {
        Store store = StoreServiceUtils.findStoreByStoreIdAndMemberId(storeRepository, storeId, memberId);
        if (store.getSalesStatus() == StoreSalesStatus.OPEN) {
            throw new DuplicatedException(String.format("<%s>의 가게는 열려있습니다. 먼저 영업을 종료해주세요.", store.getId()));
        }
        store.delete();
    }

}
