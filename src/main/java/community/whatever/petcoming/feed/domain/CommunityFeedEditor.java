package community.whatever.petcoming.feed.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class CommunityFeedEditor {

    private final CommunityFeedRepository communityFeedRepository;

    @Transactional
    public void increaseViewCount(Long feedId) {
        communityFeedRepository.increaseViewCountById(feedId);
    }
}
