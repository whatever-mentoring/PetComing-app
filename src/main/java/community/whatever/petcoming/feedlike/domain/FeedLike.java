package community.whatever.petcoming.feedlike.domain;

import community.whatever.petcoming.common.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
        name = "feed_like",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"feed_id", "liker_id"}
        ))
public class FeedLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "feed_id")
    private Long feedId; // Feed fk

    @Column(name = "liker_id")
    private Long likerId; // Member fk
}
