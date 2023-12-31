package community.whatever.petcoming.feed.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedLikeRepository extends JpaRepository<FeedLike, Long> {

    Long countFeedLikeByFeedCategoryAndFeedId(String feedCategory, Long feedId);

    boolean existsByFeedCategoryAndFeedIdAndLikerId(String feedCategory, Long feedId, Long likerId);

    void deleteByFeedCategoryAndFeedIdAndLikerId(String feedCategory, Long feedId, Long likerId);
}
