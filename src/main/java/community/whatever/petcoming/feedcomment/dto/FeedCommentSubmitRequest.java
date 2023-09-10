package community.whatever.petcoming.feedcomment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FeedCommentSubmitRequest {

    private String category; // community, lost
    private String comment;
}
