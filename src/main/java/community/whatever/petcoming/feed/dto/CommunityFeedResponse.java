package community.whatever.petcoming.feed.dto;

import community.whatever.petcoming.feed.domain.CommunityFeedInfoDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommunityFeedResponse {

    private final Long feedId;
    private final String title;
    private final String authorName;

    private final String imageUrl;

    private final Long viewCount;
    private final Long likeCount;

    @Builder
    public CommunityFeedResponse(Long feedId, String title, String authorName, String imageUrl, Long viewCount, Long likeCount) {
        this.feedId = feedId;
        this.title = title;
        this.authorName = authorName;
        this.imageUrl = imageUrl;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
    }

    public static CommunityFeedResponse of(CommunityFeedInfoDto dto) {
        return CommunityFeedResponse.builder()
                .feedId(dto.getFeedId())
                .title(dto.getTitle())
                .authorName(dto.getAuthorName())
                .imageUrl(dto.getImageUrl())
                .viewCount(dto.getViewCount())
                .likeCount(dto.getLikeCount())
                .build();
    }
}