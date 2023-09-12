package community.whatever.petcoming.feed.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CommunityFeedEditor {

    private final CommunityFeedRepository communityFeedRepository;
    private final FeedLikeFinder feedLikeFinder;

    public void increaseViewCount(Long feedId) {
        communityFeedRepository.increaseViewCountById(feedId);
    }

    public Long likeFeed(Long memberId, Long feedId) {
        boolean liked = feedLikeFinder.existsByFeedCategoryAndFeedIdAndLikerId("community", feedId, memberId);
        if (liked == false) {
            feedLikeFinder.save("community", feedId, memberId);
        }
        return feedLikeFinder.countFeedLikeByFeedCategoryAndFeedId("community", feedId);
    }

    public Long unlikeFeed(Long memberId, Long feedId) {
        boolean liked = feedLikeFinder.existsByFeedCategoryAndFeedIdAndLikerId("community", feedId, memberId);
        if (liked == true) {
            feedLikeFinder.delete("community", feedId, memberId);
        }
        return feedLikeFinder.countFeedLikeByFeedCategoryAndFeedId("community", feedId);
    }
}
