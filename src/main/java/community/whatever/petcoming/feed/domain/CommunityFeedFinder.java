package community.whatever.petcoming.feed.domain;

import community.whatever.petcoming.member.domain.Member;
import community.whatever.petcoming.member.domain.MemberFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CommunityFeedFinder {

    private final CommunityFeedRepository communityFeedRepository;
    private final MemberFinder memberFinder;

    public CommunityFeed findById(Long feedId) {
        return communityFeedRepository.findById(feedId).orElseThrow();
    }

    public void existsOrThrowById(Long feedId) {
        if (!communityFeedRepository.existsById(feedId)) {
            throw new EntityNotFoundException("CommunityFeedId="+ feedId + " not found");
        }
    }

    public List<CommunityFeedInfoDto> getCommunityFeedInfoList(Long lastFeedId, Integer size, FeedsSortOption sort) {
        Pageable pageable = sort.getPageable(size);

        List<CommunityFeed> communityFeeds = communityFeedRepository.findByIdLessThan(lastFeedId, pageable);

        List<CommunityFeedInfoDto> dtoList = new ArrayList<>();
        for (CommunityFeed feed : communityFeeds) {
            Member author = memberFinder.findById(feed.getAuthorId());
            String authorNickname = author.getNickname();

            CommunityFeedInfoDto dto = CommunityFeedInfoDto.builder()
                    .feedId(feed.getId())
                    .title(feed.getTitle())
                    .authorName(authorNickname)
                    .viewCount(feed.getViewCount())
                    .likeCount(100L)
                    .imageUrl("https://i.namu.wiki/i/BMOGQ_hFSF4xHK_oOo127aa5LHsxE28Kkomve6Yt4hfKQkAPWaqEIqsaCN2rVq2QnsLz3QFihlMF9ACZfjeK0XeB7j2GUEkIz1kJkm6c_pMwN4wwGSBBugiJ0QYQm7A2IDPXlw_9y9GzOxPJHsSx4g.webp")
                    .build();
            dtoList.add(dto);
        }

        return dtoList;
    }

    public CommunityFeedFullDto getCommunityFeedFull(Long feedId) {
        CommunityFeed feed = findById(feedId);
        String content = feed.getContent();
        Member author = memberFinder.findById(feed.getAuthorId());
        String authorNickname = author.getNickname();

        return CommunityFeedFullDto.builder()
                .feedId(feedId)
                .title(feed.getTitle())
                .authorName(authorNickname)
                .viewCount(feed.getViewCount())
                .likeCount(100L)
                .imageUrl("https://i.namu.wiki/i/BMOGQ_hFSF4xHK_oOo127aa5LHsxE28Kkomve6Yt4hfKQkAPWaqEIqsaCN2rVq2QnsLz3QFihlMF9ACZfjeK0XeB7j2GUEkIz1kJkm6c_pMwN4wwGSBBugiJ0QYQm7A2IDPXlw_9y9GzOxPJHsSx4g.webp")
                .content(content)
                .createDate(feed.getCreateDate())
                .build();
    }
}
