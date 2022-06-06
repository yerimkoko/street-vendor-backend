package store.streetvendor.service.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.domain.domain.admin.Admin;
import store.streetvendor.domain.domain.admin.AdminRepository;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;
import store.streetvendor.domain.domain.sign_out_member.SignOutMember;
import store.streetvendor.domain.domain.sign_out_member.SignOutMemberRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AdminServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private SignOutMemberRepository signOutMemberRepository;

    @Autowired
    private AdminService adminService;

    @Test
    void 관리자가_사용자를_탈퇴시킨다() {
        // given
        String email = "dddd";
        Member member = memberRepository.save(Member.newGoogleInstance("테스트용이름", "붕어왕", "bungeo@gmail.com", "s3.dd"));
        Admin admin = adminRepository.save(new Admin(email, "2222"));

        // when
        adminService.signOutMember(member.getId(), admin.getId());

        // then
        List<SignOutMember> signOutMembers = signOutMemberRepository.findAll();
        assertThat(signOutMembers).hasSize(1);
        assertThat(signOutMembers.get(0).getEmail()).isEqualTo(member.getEmail());

    }

}
