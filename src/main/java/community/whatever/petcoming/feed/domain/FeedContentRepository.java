package community.whatever.petcoming.feed.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedContentRepository extends JpaRepository<FeedContent, Long> {
}
