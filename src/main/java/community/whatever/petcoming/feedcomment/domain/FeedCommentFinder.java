package community.whatever.petcoming.feedcomment.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class FeedCommentFinder {

    private final FeedCommentRepository feedCommentRepository;

    public List<FeedComment> findListByFeedCategoryAndFeedId(String feedCategory, Long feedId) {
        return feedCommentRepository.findByFeedCategoryAndFeedId(feedCategory, feedId);
    }
}
