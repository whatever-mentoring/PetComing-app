package community.whatever.petcoming.feed.service;

import community.whatever.petcoming.feed.domain.CommunityFeedFinder;
import community.whatever.petcoming.feed.domain.CommunityFeedFullDto;
import community.whatever.petcoming.feed.domain.CommunityFeedInfoDto;
import community.whatever.petcoming.feed.domain.FeedsSortOption;
import community.whatever.petcoming.feed.dto.CommunityFeedFullResponse;
import community.whatever.petcoming.feed.dto.CommunityFeedInfoResponse;
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
    public List<CommunityFeedInfoResponse> getCommunityFeedInfoList(Integer size, FeedsSortOption sort) {
        // 내부 코드 호출
        return getCommunityFeedInfoList(Long.MAX_VALUE, size, sort);
    }

    @Transactional(readOnly = true)
    public List<CommunityFeedInfoResponse> getCommunityFeedInfoList(Long lastFeedId, Integer size, FeedsSortOption sort) {
        List<CommunityFeedInfoDto> dtoList = communityFeedFinder.getCommunityFeedInfoList(lastFeedId, size, sort);

        return dtoList.stream()
                .map(CommunityFeedInfoResponse::of)
                .collect(Collectors.toList());
    }

    public CommunityFeedFullResponse getCommunityFeedFull(Long feedId) {
        CommunityFeedFullDto fullDto = communityFeedFinder.getCommunityFeedFull(feedId);
        return CommunityFeedFullResponse.of(fullDto);
    }
}
