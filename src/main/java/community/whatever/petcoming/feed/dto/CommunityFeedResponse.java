package community.whatever.petcoming.feed.dto;

import community.whatever.petcoming.feed.domain.CommunityFeedInfoDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommunityFeedResponse {

    private final Long feedId;
    private final String title;
    private final String authorName;

    private final String imageUrl;

    private final Long viewCount;
    private final Long likeCount;

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
