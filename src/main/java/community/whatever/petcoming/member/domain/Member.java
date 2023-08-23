package community.whatever.petcoming.member.domain;

import community.whatever.petcoming.common.domain.BaseEntity;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@Table(name = "member")
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true, length = 255)
    private String email;

    @Column(name = "nickname",unique = true, length = 255)
    private String nickname;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 10)
    private MemberRole role;

    @Column(name = "refresh_token", length = 512)
    private String refreshToken;

    @Embedded
    private SocialUserInfo socialUserInfo;

    private SocialUserInfo getSocialUserInfo() {
        return socialUserInfo;
    }
}
