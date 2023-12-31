package community.whatever.petcoming.config.security.authentication;

import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        // 세션에서 JSESSIONID 가져오기
        String sessionId = request.getSession(false).getId();

        // maxAge 설정. 1일로 설정.
        int maxAge = 24 * 60 * 60;

        ResponseCookie cookie = ResponseCookie.from("JSESSIONID", sessionId)
                .domain(".petcoming.xyz")
                .path("/")
                .httpOnly(true)
                .secure(true)  // Secure flag 추가
                .sameSite("Lax")  // SameSite policy 추가
                .maxAge(maxAge)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
        response.sendRedirect("https://petcoming.xyz/");
    }
}
