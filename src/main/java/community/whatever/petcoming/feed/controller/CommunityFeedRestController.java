package community.whatever.petcoming.feed.controller;

import community.whatever.petcoming.feed.domain.FeedsSortOption;
import community.whatever.petcoming.feed.dto.CommunityFeedResponse;
import community.whatever.petcoming.feed.service.CommunityFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/feed/community")
public class CommunityFeedRestController {

    private final CommunityFeedService communityFeedService;

    @GetMapping(params = {"size"})
    public ResponseEntity<List<CommunityFeedResponse>> getCommunityFeedInfoList(
            @RequestParam Integer size,
            @RequestParam(required = false) FeedsSortOption sort
    ) {
        if (sort == null) {sort = FeedsSortOption.LATEST;}
        return ResponseEntity.ok().body(communityFeedService.getCommunityFeedInfoList(size, sort));
    }

    @GetMapping(params = {"last-feed", "size"})
    public ResponseEntity<List<CommunityFeedResponse>> getCommunityFeedInfoList(
            @RequestParam("last-feed") Long lastFeedId,
            @RequestParam Integer size,
            @RequestParam(required = false) FeedsSortOption sort
    ) {
        if (sort == null) {sort = FeedsSortOption.LATEST;}
        return ResponseEntity.ok().body(communityFeedService.getCommunityFeedInfoList(lastFeedId, size, sort));
    }
}