package store.streetvendor.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;
import store.streetvendor.domain.domain.member.MemberStatus;
import store.streetvendor.exception.model.ErrorCode;
import store.streetvendor.exception.model.NotFoundException;
import store.streetvendor.exception.model.UnAuthorizedException;
import store.streetvendor.service.member.MemberServiceUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import static store.streetvendor.config.auth.AuthConstants.MEMBER_ID;

@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String BEARER_PREFIX = "Bearer ";

    private final SessionRepository<? extends Session> sessionRepository;

    private final MemberRepository memberRepository;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
        if (auth == null) {
            return true;
        }
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasLength(header) && header.startsWith(BEARER_PREFIX)) {
            String sessionId = removeBearer(header);
            Long userId = getUserId(sessionId);
            request.setAttribute(MEMBER_ID, userId);

            return true;
        }
        throw new UnAuthorizedException(String.format("비어 있거나 Bearer 타입이 아닌 잘못된 헤더 (%S) 입니다", header));
    }

    private Long getUserId(String sessionId) {
        Session session = sessionRepository.getSession(sessionId);
        if (session == null ) {
            throw new UnAuthorizedException(ErrorCode.UNAUTHORIZED_EXCEPTION.getMessage() + String.format("세션(%s) 이 존재하지 않아요 ", sessionId));
        }
        return session.getAttribute(MEMBER_ID);
    }

    private String removeBearer(String header) {
        return header.split(BEARER_PREFIX)[1];
    }

}
