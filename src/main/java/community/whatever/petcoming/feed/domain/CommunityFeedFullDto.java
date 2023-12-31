package community.whatever.petcoming.feed.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class CommunityFeedFullDto {

    private final Long feedId;
    private final String title;
    private final String authorName;

    private final Long viewCount;
    private final Long likeCount;

    private final Boolean liked;

    private final List<String> imageUrls;
    private final String content;

    private final LocalDateTime createDate;
}
