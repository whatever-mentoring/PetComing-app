package community.whatever.petcoming.feedcomment.domain;

import community.whatever.petcoming.feed.domain.CommunityFeedFinder;
import community.whatever.petcoming.feed.domain.LostPetFeedFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FeedCommentEditor {

    private final FeedCommentRepository feedCommentRepository;

    private final LostPetFeedFinder lostPetFeedFinder;
    private final CommunityFeedFinder communityFeedFinder;

    public void saveFeedComment(Long memberId, Long feedId, String category, String comment) {
        if (category.equals("community")) {
            communityFeedFinder.existsOrThrowById(feedId);
        } else if (category.equals("lost")) {
            lostPetFeedFinder.existsOrThrowById(feedId);
        }

        FeedComment feedComment = FeedComment.builder()
                .content(comment)
                .feedId(feedId)
                .authorId(memberId)
                .build();

        feedCommentRepository.save(feedComment);
    }
}
