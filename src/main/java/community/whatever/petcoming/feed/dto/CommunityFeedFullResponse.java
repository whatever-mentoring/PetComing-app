package community.whatever.petcoming.feed.dto;

import community.whatever.petcoming.feed.domain.CommunityFeedFullDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class CommunityFeedFullResponse {

    private final Long feedId;
    private final String title;
    private final String authorName;

    private final Long viewCount;
    private final Long likeCount;

    private final Boolean liked;

    private final List<String> imageUrls;
    private final String content;

    private final LocalDateTime createDate;

    public static CommunityFeedFullResponse of(CommunityFeedFullDto fullDto) {
        return CommunityFeedFullResponse.builder()
                .feedId(fullDto.getFeedId())
                .title(fullDto.getTitle())
                .authorName(fullDto.getAuthorName())
                .viewCount(fullDto.getViewCount())
                .likeCount(fullDto.getLikeCount())
                .liked(fullDto.getLiked())
                .imageUrls(fullDto.getImageUrls())
                .content(fullDto.getContent())
                .createDate(fullDto.getCreateDate())
                .build();
    }
}
