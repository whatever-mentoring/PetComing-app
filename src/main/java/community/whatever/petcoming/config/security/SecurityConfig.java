package community.whatever.petcoming.config.security;

import community.whatever.petcoming.config.security.authentication.AjaxAuthenticationEntryPoint;
import community.whatever.petcoming.config.security.authentication.CustomLoginSuccessHandler;
import community.whatever.petcoming.config.security.authentication.CustomLogoutSuccessHandler;
import community.whatever.petcoming.member.service.CustomOidcUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOidcUserService customOidcUserService;

    // 정적 파일에 대한 접근 검사 Skip
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                PathRequest.toStaticResources().atCommonLocations()
        );
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/api/v1/member/auth-check", "/api/v1/feed/community/**", "/api/v1/feed/lost-pet/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new AjaxAuthenticationEntryPoint())
                .and()
                .oauth2Login(
                        oauth2 -> oauth2
                                .userInfoEndpoint(
                                        userInfoEndpointConfig -> userInfoEndpointConfig.oidcUserService(customOidcUserService)
                                )
                                .successHandler(new CustomLoginSuccessHandler())
                )
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
        ;
        return http.build();
    }
}
