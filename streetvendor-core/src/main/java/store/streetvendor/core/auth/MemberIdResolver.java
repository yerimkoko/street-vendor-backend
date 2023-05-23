package store.streetvendor.core.auth;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import store.streetvendor.core.exception.UnAuthorizedException;

import javax.validation.constraints.NotNull;

import static store.streetvendor.core.auth.AuthConstants.MEMBER_ID;

@Component
public class MemberIdResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.getParameterAnnotation(MemberId.class) != null;
        boolean isMatchType = parameter.getParameterType().equals(Long.class);
        return hasAnnotation && isMatchType;
    }

    @Override
    public Object resolveArgument(@NotNull MethodParameter parameter, ModelAndViewContainer mavContainer, @NotNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        if (parameter.getMethodAnnotation(Auth.class) == null) {
            throw new UnAuthorizedException(String.format("인증이 필요한 컨트롤러에요. @Auth 어노테이션을 추가해주세요. 컨트롤러: (%s)", parameter.getMethod()));
        }
        return webRequest.getAttribute(MEMBER_ID, 0);
    }

}
