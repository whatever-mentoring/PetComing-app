package community.whatever.petcoming.feed.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommunityFeedRepository extends JpaRepository<CommunityFeed, Long> {

    List<CommunityFeed> findByIdLessThan(Long lastFeedId, Pageable pageable);

    @Modifying
    @Query("UPDATE CommunityFeed f SET f.viewCount = f.viewCount + 1 WHERE f.id = :id")
    void increaseViewCountById(Long id);
}
