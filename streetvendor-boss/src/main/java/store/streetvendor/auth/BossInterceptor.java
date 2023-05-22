package store.streetvendor.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import store.streetvendor.core.exception.ErrorCode;
import store.streetvendor.core.exception.UnAuthorizedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import static store.streetvendor.auth.BossConstants.BOSS_ID;


@RequiredArgsConstructor
@Component
public class BossInterceptor implements HandlerInterceptor {

    private static final String BEARER_PREFIX = "Bearer ";

    private final SessionRepository<? extends Session> sessionRepository;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Boss boss = handlerMethod.getMethodAnnotation(Boss.class);
        if (boss == null) {
            return true;
        }
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasLength(header) && header.startsWith(BEARER_PREFIX)) {
            String sessionId = removeBearer(header);
            Long bossId = getBossId(sessionId);
            request.setAttribute(BOSS_ID, bossId);
            return true;
        }
        throw new UnAuthorizedException(String.format("비어 있거나 Bearer 타입이 아닌 잘못된 헤더 (%S) 입니다", header));
    }

    private Long getBossId(String sessionId) {
        Session session = sessionRepository.getSession(sessionId);
        if (session == null) {
            throw new UnAuthorizedException(ErrorCode.UNAUTHORIZED_EXCEPTION.getMessage() + String.format("세션 [%s]이 존재하지 않습니다. 다시 확인해주세요.", sessionId));
        }
        return session.getAttribute(BOSS_ID);
    }

    private String removeBearer(String header) {
        return header.split(BEARER_PREFIX)[1];
    }

}
