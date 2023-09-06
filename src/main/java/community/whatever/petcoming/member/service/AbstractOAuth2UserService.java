package community.whatever.petcoming.member.service;

import community.whatever.petcoming.member.domain.Member;
import community.whatever.petcoming.member.domain.MemberFinder;
import community.whatever.petcoming.member.domain.MemberRegister;
import community.whatever.petcoming.member.domain.oauth.KakaoUser;
import community.whatever.petcoming.member.domain.oauth.ProviderUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
@Slf4j
@Service
public abstract class AbstractOAuth2UserService {

    private final MemberFinder memberFinder;
    private final MemberRegister memberRegister;

    public void register(ProviderUser providerUser, OAuth2UserRequest userRequest) {
        Optional<Member> optionalMember = memberFinder.findOptionalByProviderIdAndSubject(providerUser.getProvider(), providerUser.getId());

        if (optionalMember.isEmpty()) {
            String registrationId = userRequest.getClientRegistration().getRegistrationId();
            memberRegister.register(registrationId, providerUser);
        } else {
            log.info("The user is already registered with this social provider.");
        }
    }

    public ProviderUser providerUser(ClientRegistration clientRegistration, OAuth2User oAuth2User) {

        String registrationId = clientRegistration.getRegistrationId();

        if (registrationId.equals("kakao")) {
            return new KakaoUser((OidcUser)oAuth2User, clientRegistration);
        }
        return null;
    }
}
