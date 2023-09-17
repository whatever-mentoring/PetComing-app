package community.whatever.petcoming.feedcomment.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class FeedCommentResponse {

    private String category; // community, lost
    private Long feedId;

    private Long authorId;
    private String authorNickname;

    private String content;

    private LocalDateTime createDate;
}
