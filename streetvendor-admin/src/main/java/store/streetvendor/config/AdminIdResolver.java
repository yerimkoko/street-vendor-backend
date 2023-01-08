package store.streetvendor.config;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import store.streetvendor.core.exception.UnAuthorizedException;

import static store.streetvendor.config.AdminConstants.ADMIN_ID;

@Component
public class AdminIdResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.getParameterAnnotation(AdminId.class) != null;
        boolean isMatchType = parameter.getParameterType().equals(Long.class);
        return hasAnnotation && isMatchType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        if (parameter.getMethodAnnotation(Admin.class) == null) {
            throw new UnAuthorizedException(String.format("인증이 필요한 컨트롤러 입니다. @Admin 어노테이션을 붙여주세요. 컨트롤러: [%s]", parameter.getMethod()));
        }
        return webRequest.getAttribute(ADMIN_ID, 0);
    }
}
