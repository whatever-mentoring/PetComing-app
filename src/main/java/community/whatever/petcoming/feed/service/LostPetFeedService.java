package community.whatever.petcoming.feed.service;

import community.whatever.petcoming.feed.domain.FeedsSortOption;
import community.whatever.petcoming.feed.domain.LostPetFeedFinder;
import community.whatever.petcoming.feed.domain.LostPetFeedInfoDto;
import community.whatever.petcoming.feed.dto.LostPetFeedInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LostPetFeedService {

    private final LostPetFeedFinder lostPetFeedFinder;

    @Transactional(readOnly = true)
    public List<LostPetFeedInfoResponse> getLostPetFeedInfoList(Integer size, FeedsSortOption sort) {
        // 클래스 내부 코드 호출
        return getLostPetFeedInfoList(Long.MAX_VALUE, size, sort);
    }

    @Transactional(readOnly = true)
    public List<LostPetFeedInfoResponse> getLostPetFeedInfoList(Long lastFeedId, Integer size, FeedsSortOption sort) {
        List<LostPetFeedInfoDto> dtoList = lostPetFeedFinder.getLostPetFeedInfoList(lastFeedId, size, sort);

        return dtoList.stream()
                .map(LostPetFeedInfoResponse::of)
                .collect(Collectors.toList());
    }
}
