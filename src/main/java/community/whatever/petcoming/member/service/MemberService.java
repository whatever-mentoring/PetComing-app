package community.whatever.petcoming.member.service;

import community.whatever.petcoming.member.domain.Member;
import community.whatever.petcoming.member.domain.MemberFinder;
import community.whatever.petcoming.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberFinder memberFinder;

    @Transactional(readOnly = true)
    public MemberResponse getMemberResponseByMemberId(Long memberId) {
        Member member = memberFinder.findById(memberId);
        return new MemberResponse(member.getId(), member.getNickname());
    }

    @Transactional(readOnly = true)
    public Long findIdByProviderIdAndSubject(String providerId, String subject) {
        Member member = memberFinder.findByProviderIdAndSubject(providerId, subject);
        return member.getId();
    }
}
