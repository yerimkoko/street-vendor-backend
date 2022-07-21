package store.streetvendor.service;

import store.streetvendor.controller.dto.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.controller.dto.request.SignOutMemberRequest;
import store.streetvendor.domain.domain.admin.Admin;
import store.streetvendor.domain.domain.admin.AdminRepository;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;
import store.streetvendor.domain.domain.model.exception.DuplicatedException;
import store.streetvendor.domain.domain.model.exception.NotFoundException;
import store.streetvendor.domain.domain.sign_out_member.SignOutMemberRepository;
import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.repository.StoreRepository;
import store.streetvendor.domain.domain.store.StoreSalesStatus;
import store.streetvendor.domain.service.utils.MemberServiceUtils;
import store.streetvendor.domain.service.utils.StoreServiceUtils;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final AdminRepository adminRepository;

    private final MemberRepository memberRepository;

    private final StoreRepository storeRepository;

    private final SignOutMemberRepository signOutMemberRepository;

    private final HttpSession httpSession;

    private final PasswordEncoder passwordEncoder;


    // TODO: adminId는 @AdminId로 받아서 확인할 수 있도록.
    @Transactional
    public Long signOutMember(SignOutMemberRequest request) {
        Member member = memberRepository.findMemberById(request.getMemberId());
        MemberServiceUtils.findByMemberId(memberRepository, request.getMemberId());
        Admin admin = adminRepository.findByAdminId(request.getAdminId());
        if (admin == null) {
            throw new DuplicatedException(String.format("<%s>는 관리자가 아닙니다. 관리자로 다시 로그인 해 주세요.", request.getAdminId()));
        }
        signOutMemberRepository.save(member.signOut());
        return member.getId();
    }

    @Transactional
    public void signUp(String email, String password) {
        Admin admin = adminRepository.findByEmail(email);
        if (admin != null) {
            throw new DuplicatedException(String.format("<%s>은 이미 존재합니다. 다른 이메일을 입력해 주세요.", email));
        }
        String encodePassword = passwordEncoder.encode(password);
        adminRepository.save(Admin.newAdmin(email, encodePassword));
    }

    @Transactional
    public Long login(LoginRequest loginRequest) {
        Admin admin = adminRepository.findByEmail(loginRequest.getEmail());
        if (admin == null) {
            throw new NotFoundException(String.format("<%s>에 해당하는 멤버는 존재하지 않습니다.", loginRequest.getEmail()));
        }
        passwordEncoder.matches(loginRequest.getPassword(), passwordEncoder.encode(loginRequest.getPassword()));
        httpSession.getAttribute(admin.getEmail());
        return null;

    }

    @Transactional
    public void updateStoreStatus(Long storeId, Long adminId, StoreSalesStatus salesStatus) {
        Store store = StoreServiceUtils.findByStoreId(storeRepository, storeId);
        if (store == null) {
            throw new NotFoundException(String.format("<%s>는 존재하는 아이디가 아닙니다.", storeId));
        }
        Admin admin = adminRepository.findByAdminId(adminId);
        if (admin == null) {
            throw new NotFoundException(String.format("<%s>는 존재하는 아이디가 아닙니다.", storeId));
        }

        store.changeSalesStatus(salesStatus);
    }

}
