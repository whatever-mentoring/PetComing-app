package community.whatever.petcoming.feed.dto;

import community.whatever.petcoming.feed.domain.CommunityFeedInfoDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommunityFeedInfoResponse {

    private final Long feedId;
    private final String title;
    private final String authorName;

    private final String imageUrl;

    private final Long viewCount;
    private final Long likeCount;

    private final Boolean liked;

    public static CommunityFeedInfoResponse of(CommunityFeedInfoDto dto) {
        return CommunityFeedInfoResponse.builder()
                .feedId(dto.getFeedId())
                .title(dto.getTitle())
                .authorName(dto.getAuthorName())
                .imageUrl(dto.getImageUrl())
                .viewCount(dto.getViewCount())
                .likeCount(dto.getLikeCount())
                .liked(dto.getLiked())
                .build();
    }
}
