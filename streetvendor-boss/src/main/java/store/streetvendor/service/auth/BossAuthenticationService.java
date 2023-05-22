package store.streetvendor.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.core.config.auth.dto.request.AuthRequest;
import store.streetvendor.core.config.auth.dto.response.AuthResponse;
import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.domain.member.MemberRepository;
import store.streetvendor.external.google.GoogleApiCaller;
import store.streetvendor.external.google.dto.response.GoogleUserInfoResponse;

import javax.servlet.http.HttpSession;

import static store.streetvendor.auth.BossConstants.BOSS_ID;

@RequiredArgsConstructor
@Service
public class BossAuthenticationService {

    private final HttpSession httpSession;

    private final GoogleApiCaller googleApiCaller;

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public AuthResponse handleBossGoogleAuthentication(AuthRequest request) {

        GoogleUserInfoResponse bossInfoResponse = googleApiCaller.getGoogleUserProfileInfo(request.getRequestToken());

        Member boss = memberRepository.findMemberByEmail(bossInfoResponse.getEmail());

        if (boss == null) {
            return AuthResponse.signUp(bossInfoResponse.getEmail(), bossInfoResponse.getName(), bossInfoResponse.getPicture());
        }

        httpSession.setAttribute(BOSS_ID, boss.getId());

        return AuthResponse.logIn(httpSession.getId());

    }

}
