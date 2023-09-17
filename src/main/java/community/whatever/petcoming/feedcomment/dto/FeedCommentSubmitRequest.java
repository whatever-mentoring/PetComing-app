package community.whatever.petcoming.feedcomment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FeedCommentSubmitRequest {

    @NotNull
    private String category; // community, lost
    @NotNull
    private Long feedId;
    @NotEmpty
    private String comment;
}
