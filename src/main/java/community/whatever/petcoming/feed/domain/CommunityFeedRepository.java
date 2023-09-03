package community.whatever.petcoming.feed.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityFeedRepository extends JpaRepository<CommunityFeed, Long> {

    List<CommunityFeed> findByIdLessThan(Long lastFeedId, Pageable pageable);
}
