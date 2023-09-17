package community.whatever.petcoming.feedcomment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FeedCommentListRequest {

    private String category; // community, lost
    private Long feedId;
}
