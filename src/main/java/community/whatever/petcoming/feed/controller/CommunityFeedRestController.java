package community.whatever.petcoming.feed.controller;

import community.whatever.petcoming.config.resolver.LoginMemberId;
import community.whatever.petcoming.feed.domain.FeedsSortOption;
import community.whatever.petcoming.feed.dto.CommunityFeedSubmitRequest;
import community.whatever.petcoming.feed.dto.CommunityFeedFullResponse;
import community.whatever.petcoming.feed.dto.CommunityFeedInfoResponse;
import community.whatever.petcoming.feed.service.CommunityFeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/feed/community")
public class CommunityFeedRestController {

    private final CommunityFeedService communityFeedService;

    @GetMapping(params = {"size"})
    public ResponseEntity<List<CommunityFeedInfoResponse>> getCommunityFeedInfoList(
            @LoginMemberId Long memberId,
            @RequestParam Integer size,
            @RequestParam(required = false) FeedsSortOption sort
    ) {
        if (sort == null) {
            sort = FeedsSortOption.LATEST;
        }
        return ResponseEntity.ok().body(communityFeedService.getCommunityFeedInfoList(memberId, size, sort));
    }

    @GetMapping(params = {"last-feed", "size"})
    public ResponseEntity<List<CommunityFeedInfoResponse>> getCommunityFeedInfoList(
            @LoginMemberId Long memberId,
            @RequestParam("last-feed") Long lastFeedId,
            @RequestParam Integer size,
            @RequestParam(required = false) FeedsSortOption sort
    ) {
        if (sort == null) {
            sort = FeedsSortOption.LATEST;
        }
        return ResponseEntity.ok().body(communityFeedService.getCommunityFeedInfoList(memberId, lastFeedId, size, sort));
    }

    @GetMapping("/{feedId}")
    public ResponseEntity<CommunityFeedFullResponse> getCommunityFeedFull(
            @LoginMemberId Long memberId,
            @PathVariable Long feedId) {
        communityFeedService.increaseViewCount(feedId);
        return ResponseEntity.ok().body(communityFeedService.getCommunityFeedFull(memberId, feedId));
    }

    @PostMapping("/submit")
    public ResponseEntity<Void> submitFeed(
            @LoginMemberId Long memberId,
            @RequestPart(value = "dto")  CommunityFeedSubmitRequest dto,
            @RequestPart(value = "images") List<MultipartFile> imageFiles) throws IOException {
        communityFeedService.submitFeed(memberId, dto, imageFiles);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{feedId}/delete")
    public ResponseEntity<Void> deleteFeed(@LoginMemberId Long memberId, @PathVariable Long feedId) {
        communityFeedService.deleteFeed(memberId, feedId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{feedId}/like")
    public ResponseEntity<Long> likeFeed(
            @LoginMemberId Long memberId,
            @PathVariable Long feedId) {
        return ResponseEntity.ok().body(communityFeedService.likeFeed(memberId, feedId));
    }

    @PostMapping("{feedId}/unlike")
    public ResponseEntity<Long> unlikeFeed(
            @LoginMemberId Long memberId,
            @PathVariable Long feedId) {
        return ResponseEntity.ok().body(communityFeedService.unlikeFeed(memberId, feedId));
    }
}
