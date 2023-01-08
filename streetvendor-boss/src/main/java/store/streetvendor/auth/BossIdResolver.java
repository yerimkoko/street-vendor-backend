package store.streetvendor.auth;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import store.streetvendor.core.exception.UnAuthorizedException;

import javax.validation.constraints.NotNull;

import static store.streetvendor.auth.BossConstants.BOSS_ID;

@Component
public class BossIdResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.getParameterAnnotation(BossId.class) != null;
        boolean isMatchType = parameter.getParameterType().equals(Long.class);
        return hasAnnotation && isMatchType;
    }

    @Override
    public Object resolveArgument(@NotNull MethodParameter parameter, ModelAndViewContainer mavContainer, @NotNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        if (parameter.getMethodAnnotation(Boss.class) == null)
            throw new UnAuthorizedException(String.format("인증이 필요한 컨트롤러 입니다. @Both 어노테이션을 붙여주세요. 컨트롤러: [%s]", parameter.getMethod()));
        return webRequest.getAttribute(BOSS_ID, 0);
    }

}
