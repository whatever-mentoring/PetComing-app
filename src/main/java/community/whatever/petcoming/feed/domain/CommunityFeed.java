package community.whatever.petcoming.feed.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Entity
@Table(name = "community_feed")
public class CommunityFeed extends Feed {

    @Builder
    public CommunityFeed(String title, FeedContent content, Long authorId) {
        super(title, content, authorId);
    }
}
