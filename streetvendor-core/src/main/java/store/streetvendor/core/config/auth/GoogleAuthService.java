package store.streetvendor.core.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.domain.member.MemberRepository;
import store.streetvendor.external.google.GoogleApiCaller;
import store.streetvendor.external.google.dto.response.GoogleUserInfoResponse;
import store.streetvendor.core.config.auth.dto.request.AuthRequest;
import store.streetvendor.core.config.auth.dto.response.AuthResponse;

import javax.servlet.http.HttpSession;

import static store.streetvendor.core.config.auth.AuthConstants.MEMBER_ID;

@RequiredArgsConstructor
@Service
public class GoogleAuthService {

    private final HttpSession httpSession;

    private final GoogleApiCaller googleApiCaller;

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public AuthResponse handleGoogleAuthentication(AuthRequest request) {
        GoogleUserInfoResponse userInfoResponse = googleApiCaller.getGoogleUserProfileInfo(request.getRequestToken());

        Member findMember = memberRepository.findMemberIdByEmail(userInfoResponse.getEmail());

        if (findMember == null) {
            return AuthResponse.signUp(userInfoResponse.getEmail(), userInfoResponse.getName(), userInfoResponse.getPicture());
        }

        httpSession.setAttribute(MEMBER_ID, findMember.getId());

        return AuthResponse.logIn(httpSession.getId());
    }

}
