package community.whatever.petcoming.feed.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class LostPetFeedEditor {

    private final LostPetFeedRepository lostPetFeedRepository;
    private final FeedLikeFinder feedLikeFinder;

    @Transactional
    public void increaseViewCount(Long feedId) {
        lostPetFeedRepository.increaseViewCountById(feedId);
    }

    public Long likeFeed(Long memberId, Long feedId) {
        boolean liked = feedLikeFinder.existsByFeedCategoryAndFeedIdAndLikerId("lost", feedId, memberId);
        if (liked == false) {
            feedLikeFinder.save("lost", feedId, memberId);
        }
        return feedLikeFinder.countFeedLikeByFeedCategoryAndFeedId("lost", feedId);
    }

    public Long unlikeFeed(Long memberId, Long feedId) {
        boolean liked = feedLikeFinder.existsByFeedCategoryAndFeedIdAndLikerId("lost", feedId, memberId);
        if (liked == true) {
            feedLikeFinder.delete("lost", feedId, memberId);
        }
        return feedLikeFinder.countFeedLikeByFeedCategoryAndFeedId("lost", feedId);
    }
}
