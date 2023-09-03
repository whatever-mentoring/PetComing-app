package community.whatever.petcoming.feed.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommunityFeedInfoDto {

    private final Long feedId;
    private final String title;
    private final String authorName;

    private final String imageUrl;

    private final Long viewCount;
    private final Long likeCount;

    @Builder
    public CommunityFeedInfoDto(Long feedId, String title, String authorName, String imageUrl, Long viewCount, Long likeCount) {
        this.feedId = feedId;
        this.title = title;
        this.authorName = authorName;
        this.imageUrl = imageUrl;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
    }
}
