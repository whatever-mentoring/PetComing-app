package community.whatever.petcoming.feed.controller;

import community.whatever.petcoming.config.resolver.LoginMemberId;
import community.whatever.petcoming.feed.domain.FeedsSortOption;
import community.whatever.petcoming.feed.dto.CommunityFeedFullRequest;
import community.whatever.petcoming.feed.dto.CommunityFeedFullResponse;
import community.whatever.petcoming.feed.dto.CommunityFeedInfoResponse;
import community.whatever.petcoming.feed.service.CommunityFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/feed/community")
public class CommunityFeedRestController {

    private final CommunityFeedService communityFeedService;

    @GetMapping(params = {"size"})
    public ResponseEntity<List<CommunityFeedInfoResponse>> getCommunityFeedInfoList(
            @RequestParam Integer size,
            @RequestParam(required = false) FeedsSortOption sort
    ) {
        if (sort == null) {
            sort = FeedsSortOption.LATEST;
        }
        return ResponseEntity.ok().body(communityFeedService.getCommunityFeedInfoList(size, sort));
    }

    @GetMapping(params = {"last-feed", "size"})
    public ResponseEntity<List<CommunityFeedInfoResponse>> getCommunityFeedInfoList(
            @RequestParam("last-feed") Long lastFeedId,
            @RequestParam Integer size,
            @RequestParam(required = false) FeedsSortOption sort
    ) {
        if (sort == null) {
            sort = FeedsSortOption.LATEST;
        }
        return ResponseEntity.ok().body(communityFeedService.getCommunityFeedInfoList(lastFeedId, size, sort));
    }

    @GetMapping("/{feedId}")
    public ResponseEntity<CommunityFeedFullResponse> getCommunityFeedFull(@PathVariable Long feedId) {
        communityFeedService.increaseViewCount(feedId);
        return ResponseEntity.ok().body(communityFeedService.getCommunityFeedFull(feedId));
    }

    @PostMapping("/submit")
    public ResponseEntity<Void> submitFeed(@LoginMemberId Long memberId, @ModelAttribute CommunityFeedFullRequest dto) throws IOException {
        communityFeedService.submitFeed(memberId, dto);
        return ResponseEntity.ok().build();
    }
}
