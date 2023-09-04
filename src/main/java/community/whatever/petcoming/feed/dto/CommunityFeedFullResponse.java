package community.whatever.petcoming.feed.dto;

import community.whatever.petcoming.feed.domain.CommunityFeedFullDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommunityFeedFullResponse {

    private final Long feedId;
    private final String title;
    private final String authorName;

    private final Long viewCount;
    private final Long likeCount;

    private final String imageUrl;
    private final String content;

    private final LocalDateTime createDate;

    public static CommunityFeedFullResponse of(CommunityFeedFullDto fullDto) {
        return CommunityFeedFullResponse.builder()
                .feedId(fullDto.getFeedId())
                .title(fullDto.getTitle())
                .authorName(fullDto.getAuthorName())
                .viewCount(fullDto.getViewCount())
                .likeCount(fullDto.getLikeCount())
                .imageUrl(fullDto.getImageUrl())
                .content(fullDto.getContent())
                .createDate(fullDto.getCreateDate())
                .build();
    }
}
