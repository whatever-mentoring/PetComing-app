package community.whatever.petcoming.feed.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CommunityFeedEditor {

    private final CommunityFeedRepository communityFeedRepository;

    public void increaseViewCount(Long feedId) {
        communityFeedRepository.increaseViewCountById(feedId);
    }
}
