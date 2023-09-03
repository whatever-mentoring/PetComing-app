package community.whatever.petcoming.feed.service;

import community.whatever.petcoming.feed.domain.CommunityFeedFinder;
import community.whatever.petcoming.feed.domain.CommunityFeedInfoDto;
import community.whatever.petcoming.feed.domain.FeedsSortOption;
import community.whatever.petcoming.feed.dto.CommunityFeedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommunityFeedService {

    private final CommunityFeedFinder communityFeedFinder;

    @Transactional(readOnly = true)
    public List<CommunityFeedResponse> getCommunityFeedInfoList(Integer size, FeedsSortOption sort) {
        // 내부 코드 호출
        return getCommunityFeedInfoList(Long.MAX_VALUE, size, sort);
    }

    @Transactional(readOnly = true)
    public List<CommunityFeedResponse> getCommunityFeedInfoList(Long lastFeedId, Integer size, FeedsSortOption sort) {
        List<CommunityFeedInfoDto> dtoList = communityFeedFinder.getCommunityFeedInfoList(lastFeedId, size, sort);

        return dtoList.stream()
                .map(CommunityFeedResponse::of)
                .collect(Collectors.toList());
    }
}
