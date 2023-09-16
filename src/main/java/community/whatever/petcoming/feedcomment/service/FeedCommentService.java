package community.whatever.petcoming.feedcomment.service;

import community.whatever.petcoming.feedcomment.domain.FeedComment;
import community.whatever.petcoming.feedcomment.domain.FeedCommentEditor;
import community.whatever.petcoming.feedcomment.domain.FeedCommentFinder;
import community.whatever.petcoming.feedcomment.dto.FeedCommentResponse;
import community.whatever.petcoming.feedcomment.dto.FeedCommentSubmitRequest;
import community.whatever.petcoming.member.domain.Member;
import community.whatever.petcoming.member.domain.MemberFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FeedCommentService {

    private final FeedCommentFinder feedCommentFinder;
    private final FeedCommentEditor feedCommentEditor;
    private final MemberFinder memberFinder;

    @Transactional
    public void submitFeedComment(Long memberId, FeedCommentSubmitRequest comment) {
        feedCommentEditor.saveFeedComment(memberId, comment.getCategory(), comment.getFeedId(), comment.getComment());
    }

    @Transactional(readOnly = true)
    public List<FeedCommentResponse> getCommunityFeedCommentList(String feedCategory, Long feedId) {
        List<FeedComment> feedComments = feedCommentFinder.findListByFeedCategoryAndFeedId(feedCategory, feedId);

        List<FeedCommentResponse> responseList = new ArrayList<>();
        for (FeedComment comment : feedComments) {
            Member author = memberFinder.findById(comment.getAuthorId());

            FeedCommentResponse feedCommentResponse = FeedCommentResponse.builder()
                    .category(feedCategory)
                    .feedId(comment.getId())
                    .authorId(author.getId())
                    .authorNickname(author.getNickname())
                    .content(comment.getContent())
                    .createDate(comment.getCreateDate())
                    .build();

            responseList.add(feedCommentResponse);
        }

        return responseList;
    }
}
