package community.whatever.petcoming.member.service;

import community.whatever.petcoming.member.domain.Member;
import community.whatever.petcoming.member.domain.MemberFinder;
import community.whatever.petcoming.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberFinder memberFinder;

    public MemberResponse findByProviderIdAndSubject(String providerId, String subject) {
        Member member = memberFinder.findByProviderIdAndSubject(providerId, subject);
        return new MemberResponse(member.getNickname(), member.getProfileImageUrl());
    }
}
