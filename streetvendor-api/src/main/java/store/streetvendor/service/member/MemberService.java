package store.streetvendor.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import store.streetvendor.core.aws.AwsS3Service;
import store.streetvendor.core.aws.ImageFileType;
import store.streetvendor.core.aws.request.FileUploadRequest;
import store.streetvendor.core.aws.request.ImageFileUploadRequest;
import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.domain.member.MemberRepository;
import store.streetvendor.core.utils.service.MemberServiceUtils;
import store.streetvendor.core.domain.sign_out_member.SignOutMemberRepository;
import store.streetvendor.core.exception.DuplicatedException;
import store.streetvendor.core.utils.dto.member.request.MemberSignUpRequestDto;
import store.streetvendor.core.utils.dto.member.response.MemberInfoResponse;


@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final SignOutMemberRepository signOutMemberRepository;

    private final AwsS3Service s3Service;

    @Transactional
    public Long signUp(MemberSignUpRequestDto requestDto) {
        validateDuplicatedNickName(requestDto.getNickName());
        validateDuplicatedEmail(requestDto.getEmail());
        Member member = memberRepository.save(requestDto.toEntity());
        return member.getId();
    }


    @Transactional
    public Long signOut(Long memberId) {
        Member member = MemberServiceUtils.findByMemberId(memberRepository, memberId);
        signOutMemberRepository.save(member.signOut());
        memberRepository.delete(member);
        return memberId;
    }


    @Transactional(readOnly = true)
    public MemberInfoResponse getMyInformation(Long memberId) {
        Member member = MemberServiceUtils.findByMemberId(memberRepository, memberId);
        return MemberInfoResponse.getInfo(member);
    }

    @Transactional
    public void changeProfileImage(Long memberId, MultipartFile profileUrl) {
        Member member = MemberServiceUtils.findByMemberId(memberRepository, memberId);
        FileUploadRequest request = ImageFileUploadRequest.of(profileUrl, ImageFileType.MEMBER_IMAGE);
        member.changeProfileUrl(s3Service.uploadImageFile(request).getImageUrl());
    }



    @Transactional
    public void changeNickName(Long memberId, String nickName) {
        validateDuplicatedNickName(nickName);
        Member member = MemberServiceUtils.findByMemberId(memberRepository, memberId);
        member.changeNickName(nickName);
    }


    private void validateDuplicatedNickName(String nickName) {
        Member member = memberRepository.findMemberByNickName(nickName);
        if (member != null) {
            throw new DuplicatedException(String.format("(%s)는 이미 사용중인 닉네임 입니다. 다른 닉네임을 입력해주세요.", nickName));
        }
    }

    private void validateDuplicatedEmail(String email) {
        Member member = memberRepository.findMemberIdByEmail(email);
        if (member != null) {
            throw new DuplicatedException(String.format("(%s)는 이미 가입된 회원입니다. 기존 이메일로 로그인해주세요.", email));
        }
    }

}
