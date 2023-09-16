package community.whatever.petcoming.feedcomment.controller;

import community.whatever.petcoming.config.resolver.LoginMemberId;
import community.whatever.petcoming.feedcomment.dto.FeedCommentSubmitRequest;
import community.whatever.petcoming.feedcomment.service.FeedCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/feed/comment")
public class FeedCommentRestController {

    private final FeedCommentService feedCommentService;

    @PostMapping({"/submit"})
    public ResponseEntity<Void> submitFeedComment(@LoginMemberId Long memberId, @RequestBody FeedCommentSubmitRequest dto) {
        feedCommentService.submitFeedComment(memberId, dto);
        return ResponseEntity.ok().build();
    }
}
