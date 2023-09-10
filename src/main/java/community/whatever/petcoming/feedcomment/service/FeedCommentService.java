package community.whatever.petcoming.feedcomment.service;

import community.whatever.petcoming.feedcomment.domain.FeedCommentEditor;
import community.whatever.petcoming.feedcomment.dto.FeedCommentSubmitRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FeedCommentService {

    private final FeedCommentEditor feedCommentEditor;

    @Transactional
    public void submitFeedComment(Long memberId, Long feedId, FeedCommentSubmitRequest comment) {
        feedCommentEditor.saveFeedComment(memberId, feedId, comment.getCategory(), comment.getComment());
    }
}
