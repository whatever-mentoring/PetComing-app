package community.whatever.petcoming.feedcomment.controller;

import community.whatever.petcoming.config.resolver.LoginMemberId;
import community.whatever.petcoming.feedcomment.dto.FeedCommentListRequest;
import community.whatever.petcoming.feedcomment.dto.FeedCommentResponse;
import community.whatever.petcoming.feedcomment.dto.FeedCommentSubmitRequest;
import community.whatever.petcoming.feedcomment.service.FeedCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/feed/comment")
public class FeedCommentRestController {

    private final FeedCommentService feedCommentService;

    @PostMapping("/submit")
    public ResponseEntity<Void> submitFeedComment(@LoginMemberId Long memberId, @RequestBody FeedCommentSubmitRequest dto) {
        feedCommentService.submitFeedComment(memberId, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<FeedCommentResponse>> getFeedCommentList(@ModelAttribute FeedCommentListRequest dto) {
        return ResponseEntity.ok().body(feedCommentService.getCommunityFeedCommentList(dto.getCategory(), dto.getFeedId()));
    }
}
