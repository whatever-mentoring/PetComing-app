package community.whatever.petcoming.member.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@Embeddable
public class SocialUserInfo {

    @Column(name = "provider_id", length = 32)
    private String providerId;

    @Column(name = "subject", length = 255)
    private String subject; // id

    @Column(name = "social_nickname")
    private String socialNickname;

    @Builder
    public SocialUserInfo(String providerId, String subject, String socialNickname) {
        this.providerId = providerId;
        this.subject = subject;
        this.socialNickname = socialNickname;
    }
}
