package store.streetvendor.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.streetvendor.domain.domain.admin.AdminRepository;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;
import store.streetvendor.domain.domain.sign_out_member.SignOutMemberRepository;
import store.streetvendor.service.member.MemberServiceUtils;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final AdminRepository adminRepository;

    private final MemberRepository memberRepository;

    private final SignOutMemberRepository signOutMemberRepository;

    public Long signOutMember(Long memberId, Long adminId) {
        Member member = MemberServiceUtils.findByMemberId(memberRepository, memberId);
        AdminServiceUtils.validateAdmin(adminRepository, adminId);
        signOutMemberRepository.save(member.signOut());
        return member.getId();
    }


}
