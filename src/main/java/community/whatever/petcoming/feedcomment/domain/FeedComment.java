package community.whatever.petcoming.feedcomment.domain;

import community.whatever.petcoming.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
@Table(name = "feed_comment")
public class FeedComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", length = 255)
    private String content;

    @Column(name = "feed_category")
    private String feedCategory;

    @Column(name = "feed_id")
    private Long feedId;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
