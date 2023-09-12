package community.whatever.petcoming.feed.domain;

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
import javax.persistence.UniqueConstraint;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(
        name = "feed_like",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"feed_category", "feed_id", "liker_id"}
        ))
public class FeedLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "feed_category")
    private String feedCategory;

    @Column(name = "feed_id")
    private Long feedId; // Feed fk

    @Column(name = "liker_id")
    private Long likerId; // Member fk
}
