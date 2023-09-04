package community.whatever.petcoming.member.controller;

import community.whatever.petcoming.member.dto.MemberResponse;
import community.whatever.petcoming.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/permitAll")
    public ResponseEntity<String> permitted() {
        return ResponseEntity.ok().body("hi");
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> permitted(@AuthenticationPrincipal OidcUser oidcUser) {
        String sub = (String) oidcUser.getAttributes().get("sub");
        return ResponseEntity.ok().body(memberService.findByProviderIdAndSubject("kakao", sub));
    }
}
