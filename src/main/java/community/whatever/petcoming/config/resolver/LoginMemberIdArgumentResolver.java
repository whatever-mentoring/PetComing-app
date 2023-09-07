package community.whatever.petcoming.config.resolver;

import community.whatever.petcoming.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class LoginMemberIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberService memberService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //"@LoginMemberId" Long memberId
        boolean hasLoginUserAnnotation = parameter.hasParameterAnnotation(LoginMemberId.class);
        //@LoginUser "Long" memberId
        boolean hasUserProfileType = Long.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginUserAnnotation && hasUserProfileType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        OidcUser oidcUser = (OidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (oidcUser == null) {
            throw new IllegalArgumentException("Authorization server error");
        }

        String subject = (String) oidcUser.getAttributes().get("sub");
        Long memberId = memberService.findIdByProviderIdAndSubject("kakao", subject);
        return memberId;
    }
}
