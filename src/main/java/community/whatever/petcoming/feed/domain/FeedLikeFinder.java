package community.whatever.petcoming.feed.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FeedLikeFinder {

    private final FeedLikeRepository feedLikeRepository;

    public Long countFeedLikeByFeedCategoryAndFeedId(String feedCategory, Long feedId) {
        return feedLikeRepository.countFeedLikeByFeedCategoryAndFeedId(feedCategory, feedId);
    }

    public boolean existsByFeedCategoryAndFeedIdAndLikerId(String feedCategory, Long feedId, Long likerId) {
        if (likerId == null) {
            return false;
        }
        return feedLikeRepository.existsByFeedCategoryAndFeedIdAndLikerId(feedCategory, feedId, likerId);
    }

    public FeedLike save(String feedCategory, Long feedId, Long likerId) {
        FeedLike feedLike = FeedLike.builder()
                .feedCategory(feedCategory)
                .feedId(feedId)
                .likerId(likerId)
                .build();
        return feedLikeRepository.save(feedLike);
    }

    public void delete(String feedCategory, Long feedId, Long memberId) {
        feedLikeRepository.deleteByFeedCategoryAndFeedIdAndLikerId(feedCategory, feedId, memberId);
    }
}
