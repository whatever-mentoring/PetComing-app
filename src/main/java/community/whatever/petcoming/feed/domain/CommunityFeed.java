package community.whatever.petcoming.feed.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Entity
@Table(name = "community_feed")
public class CommunityFeed extends Feed {
}
