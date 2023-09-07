package community.whatever.petcoming.feed.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class LostPetFeedEditor {

    private final LostPetFeedRepository lostPetFeedRepository;

    @Transactional
    public void increaseViewCount(Long feedId) {
        lostPetFeedRepository.increaseViewCountById(feedId);
    }
}
