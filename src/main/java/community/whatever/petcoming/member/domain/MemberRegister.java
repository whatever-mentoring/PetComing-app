package community.whatever.petcoming.member.domain;

import community.whatever.petcoming.member.domain.naming.NamingAdjective;
import community.whatever.petcoming.member.domain.naming.NamingAnimal;
import community.whatever.petcoming.member.domain.oauth.ProviderUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

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
                .nickname(makeRandomName())
                .profileImageUrl(providerUser.getPicture())
                .role(MemberRole.USER)
                .refreshToken(null)
                .socialUserInfo(socialUserInfo)
                .build();
        memberRepository.save(member);
    }

    public String makeRandomName() {
        Random random = new Random();
        NamingAdjective adjective = NamingAdjective.values()[random.nextInt(NamingAdjective.values().length)];
        NamingAnimal animal = NamingAnimal.values()[random.nextInt(NamingAnimal.values().length)];
        boolean isUnique = false;

        int number = 1;
        String username = adjective.getKorean() + animal.getKorean();
        while (!isUnique) {
            Boolean isExists = memberRepository.existsByNickname(username + number);
            if (isExists) {
                number++;
            } else {
                isUnique = true;
            }
        }

        return username + number;
    }
}
