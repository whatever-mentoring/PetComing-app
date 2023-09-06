package community.whatever.petcoming.member.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class MemberFinder {

    private final MemberRepository memberRepository;

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow();
    }

    public Optional<Member> findOptionalByProviderIdAndSubject(String providerId, String subject) {
        return memberRepository.findBySocialUserInfoProviderIdAndSocialUserInfoSubject(providerId, subject);
    }

    public Member findByProviderIdAndSubject(String providerId, String subject) {
        return memberRepository.findBySocialUserInfoProviderIdAndSocialUserInfoSubject(providerId, subject).orElseThrow();
    }
}
