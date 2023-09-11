package community.whatever.petcoming.config.security.authentication;

import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 세션에서 JSESSIONID 가져오기
        String sessionId = request.getSession(false).getId();

        // maxAge 설정. 1일로 설정.
        int maxAge = 24 * 60 * 60;

        ResponseCookie cookie = ResponseCookie.from("JSESSIONID", sessionId)
                .domain(".petcoming.xyz")
                .path("/")
                .httpOnly(true)
                .maxAge(maxAge)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
        response.sendRedirect("https://petcoming.xyz/");
    }
}
