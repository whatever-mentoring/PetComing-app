package community.whatever.petcoming.postlike.domain;

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
        name = "post_like",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"post_id", "liker_id"}
        ))
public class PostLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_id")
    private Long postId; // PostInfo fk

    @Column(name = "liker_id")
    private Long likerId; // Member fk
}
