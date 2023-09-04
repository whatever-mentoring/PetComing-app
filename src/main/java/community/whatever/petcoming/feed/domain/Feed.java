package community.whatever.petcoming.feed.domain;

import community.whatever.petcoming.common.domain.BaseEntity;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@Getter
@MappedSuperclass
public abstract class Feed extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 255) // 댕글냥글(제목), 찾아주세요(특이사항)
    private String title;

    @OneToOne(fetch = FetchType.LAZY)
    private FeedContent content;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "view_count")
    private Long viewCount;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    public String getContent() {
        return content.getContent();
    }
}
