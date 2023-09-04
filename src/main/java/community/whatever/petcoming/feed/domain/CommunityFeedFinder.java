package community.whatever.petcoming.feed.domain;

import community.whatever.petcoming.member.domain.Member;
import community.whatever.petcoming.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CommunityFeedFinder {

    private final MemberRepository memberRepository;
    private final CommunityFeedRepository communityFeedRepository;

    public CommunityFeed findById(Long feedId) {
        return communityFeedRepository.findById(feedId).orElseThrow();
    }

    public List<CommunityFeedInfoDto> getCommunityFeedInfoList(Long lastFeedId, Integer size, FeedsSortOption sort) {
        Pageable pageable = sort.getPageable(size);

        List<CommunityFeed> communityFeeds = communityFeedRepository.findByIdLessThan(lastFeedId, pageable);

        List<CommunityFeedInfoDto> dtoList = new ArrayList<>();
        for (CommunityFeed feed : communityFeeds) {
            Member author = memberRepository.findById(feed.getAuthorId()).orElseThrow();
            String authorNickname = author.getNickname();

            CommunityFeedInfoDto dto = CommunityFeedInfoDto.builder()
                    .feedId(feed.getId())
                    .title(feed.getTitle())
                    .authorName(authorNickname)
                    .viewCount(100L)
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
        Member author = memberRepository.findById(feed.getAuthorId()).orElseThrow();
        String authorNickname = author.getNickname();

        return CommunityFeedFullDto.builder()
                .feedId(feedId)
                .title(feed.getTitle())
                .authorName(authorNickname)
                .viewCount(100L)
                .likeCount(100L)
                .imageUrl("https://i.namu.wiki/i/BMOGQ_hFSF4xHK_oOo127aa5LHsxE28Kkomve6Yt4hfKQkAPWaqEIqsaCN2rVq2QnsLz3QFihlMF9ACZfjeK0XeB7j2GUEkIz1kJkm6c_pMwN4wwGSBBugiJ0QYQm7A2IDPXlw_9y9GzOxPJHsSx4g.webp")
                .content(content)
                .createDate(feed.getCreateDate())
                .build();
    }
}
