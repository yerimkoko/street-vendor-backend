package store.streetvendor.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.domain.domain.admin.AdminRepository;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;
import store.streetvendor.domain.domain.sign_out_member.SignOutMemberRepository;
import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.StoreRepository;
import store.streetvendor.domain.domain.store.StoreSalesStatus;
import store.streetvendor.service.member.MemberServiceUtils;
import store.streetvendor.service.store.StoreServiceUtils;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final AdminRepository adminRepository;

    private final MemberRepository memberRepository;

    private final StoreRepository storeRepository;

    private final SignOutMemberRepository signOutMemberRepository;


    // TODO: adminId는 @AdminId로 받아서 확인할 수 있도록.
    @Transactional
    public Long signOutMember(Long memberId, Long adminId) {
        Member member = MemberServiceUtils.findByMemberId(memberRepository, memberId);
        AdminServiceUtils.validateAdmin(adminRepository, adminId);
        signOutMemberRepository.save(member.signOut());
        return member.getId();
    }

    @Transactional
    public void updateStoreStatus(Long storeId, Long adminId, StoreSalesStatus salesStatus) {
        Store store = StoreServiceUtils.findByStoreId(storeRepository, storeId);
        AdminServiceUtils.validateAdmin(adminRepository, adminId);
        store.changeSalesStatus(salesStatus);
    }

}
