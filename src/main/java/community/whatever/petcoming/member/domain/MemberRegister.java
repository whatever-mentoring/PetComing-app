package community.whatever.petcoming.member.domain;

import community.whatever.petcoming.member.domain.oauth.ProviderUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MemberRegister {

    private final MemberRepository memberRepository;

    public void register(String registrationId, ProviderUser providerUser) {
        SocialUserInfo socialUserInfo = SocialUserInfo.builder()
                .providerId(registrationId)
                .subject(providerUser.getId())
                .socialNickname(providerUser.getUsername())
                .build();

        Member member = Member.builder()
                .email(providerUser.getEmail())
                .nickname(providerUser.getUsername())
                .profileImageUrl(providerUser.getPicture())
                .role(MemberRole.USER)
                .refreshToken(null)
                .socialUserInfo(socialUserInfo)
                .build();
        memberRepository.save(member);
    }
}
