package community.whatever.petcoming.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findBySocialUserInfoProviderIdAndSocialUserInfoSubject(String providerId, String subject);

    Boolean existsByNickname(String nickname);
}
