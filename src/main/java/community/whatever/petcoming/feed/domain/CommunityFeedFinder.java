package community.whatever.petcoming.feed.domain;

import community.whatever.petcoming.member.domain.Member;
import community.whatever.petcoming.member.domain.MemberFinder;
import community.whatever.petcoming.uploadimage.domain.UploadImage;
import community.whatever.petcoming.uploadimage.domain.UploadImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CommunityFeedFinder {

    private final CommunityFeedRepository communityFeedRepository;
    private final UploadImageRepository uploadImageRepository;
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
            UploadImage image = uploadImageRepository.findFirstByFeedCategoryAndFeedIdOrderByIdAsc("community", feed.getId()).orElseThrow();
            String authorNickname = author.getNickname();

            CommunityFeedInfoDto dto = CommunityFeedInfoDto.builder()
                    .feedId(feed.getId())
                    .title(feed.getTitle())
                    .authorName(authorNickname)
                    .viewCount(feed.getViewCount())
                    .likeCount(100L)
                    .imageUrl(image.getUploadUrl())
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

        List<UploadImage> images = uploadImageRepository.findByFeedCategoryAndFeedIdOrderByFeedIdAsc("community", feedId);
        List<String> imageUrls = images.stream()
                .map(UploadImage::getUploadUrl)
                .collect(Collectors.toList());

        return CommunityFeedFullDto.builder()
                .feedId(feedId)
                .title(feed.getTitle())
                .authorName(authorNickname)
                .viewCount(feed.getViewCount())
                .likeCount(100L)
                .imageUrls(imageUrls)
                .content(content)
                .createDate(feed.getCreateDate())
                .build();
    }
}
