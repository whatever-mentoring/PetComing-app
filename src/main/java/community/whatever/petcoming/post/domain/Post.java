package community.whatever.petcoming.post.domain;

import community.whatever.petcoming.common.domain.BaseEntity;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@NoArgsConstructor
@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 255) // 댕글냥글(제목), 찾아주세요(특이사항)
    private String title;

    @OneToOne(fetch = FetchType.LAZY)
    private PostContent content;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "view_count")
    private Long viewCount;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Builder
    public Post(Long id, String title, PostContent content, Long authorId, Long boardId, Long viewCount, boolean isDeleted) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.boardId = boardId;
        this.viewCount = viewCount;
        this.isDeleted = isDeleted;
    }
}
