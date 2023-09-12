package community.whatever.petcoming.feed.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommunityFeedInfoDto {

    private final Long feedId;
    private final String title;
    private final String authorName;

    private final Long viewCount;
    private final Long likeCount;

    private final Boolean liked;

    private final String imageUrl;
}
