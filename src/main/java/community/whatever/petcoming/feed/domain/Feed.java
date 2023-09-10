package community.whatever.petcoming.feed.domain;

import community.whatever.petcoming.common.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@NoArgsConstructor
@Getter
@MappedSuperclass
public abstract class Feed extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 255) // 댕글냥글(제목), 찾아주세요(특이사항)
    private String title;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FeedContent content;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "view_count")
    private Long viewCount;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    public Feed(String title, FeedContent content, Long authorId) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.viewCount = 0L;
        this.isDeleted = false;
    }

    public String getContent() {
        return content.getContent();
    }
}
