package com.example.streetvendoradmin.service;

import com.example.streetvendoradmin.controller.dto.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.domain.domain.admin.Admin;
import store.streetvendor.domain.domain.admin.AdminRepository;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;
import store.streetvendor.domain.domain.sign_out_member.SignOutMemberRepository;
import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.StoreRepository;
import store.streetvendor.domain.domain.store.StoreSalesStatus;
import store.streetvendor.exception.DuplicatedException;
import store.streetvendor.exception.NotFoundException;
import store.streetvendor.service.store.StoreServiceUtils;

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
    public Long signOutMember(Long memberId, Long adminId) {
        Member member = memberRepository.findMemberById(memberId);
        AdminServiceUtils.validateAdmin(adminRepository, adminId);
        signOutMemberRepository.save(member.signOut());
        return member.getId();
    }

    @Transactional
    public void signUp(String email, String password) {
        Admin admin = adminRepository.findByEmail(email);
        if (admin != null) {
            throw new DuplicatedException(String.format("<%s>은 이미 존재합니다. 다른 이메일을 입력해 주세요.", email));
        }
        // String encodePassword = passwordEncoder.encode(password);
        // adminRepository.save(Admin.newAdmin(email, encodePassword));
    }

    @Transactional
    public Long login(LoginRequest request) {
        Admin admin = adminRepository.findByEmail(request.getEmail());
        if (admin == null) {
            throw new NotFoundException(String.format("<%s>에 해당하는 멤버는 존재하지 않습니다."));
        }
        // passwordEncoder.matches(request.getPassword(), passwordEncoder.encode(request.getPassword()));
        // httpSession.getAttribute(admin.getEmail());
        return null;

    }

    @Transactional
    public void updateStoreStatus(Long storeId, Long adminId, StoreSalesStatus salesStatus) {
        Store store = StoreServiceUtils.findByStoreId(storeRepository, storeId);
        if (store == null) {
            throw new NotFoundException(String.format("<%s>는 존재하는 아이디가 아닙니다.", storeId));
        }
        AdminServiceUtils.validateAdmin(adminRepository, adminId);
        store.changeSalesStatus(salesStatus);
    }

}
