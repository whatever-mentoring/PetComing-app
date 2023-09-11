package community.whatever.petcoming.feed.controller;

import community.whatever.petcoming.config.resolver.LoginMemberId;
import community.whatever.petcoming.feed.domain.FeedsSortOption;
import community.whatever.petcoming.feed.dto.LostPetFeedFullResponse;
import community.whatever.petcoming.feed.dto.LostPetFeedInfoResponse;
import community.whatever.petcoming.feed.dto.LostPetFeedSubmitRequest;
import community.whatever.petcoming.feed.service.LostPetFeedService;
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
@RequestMapping("/api/v1/feed/lost-pet")
public class LostPetFeedRestController {

    private final LostPetFeedService lostPetFeedService;

    @GetMapping(params = {"size"})
    public ResponseEntity<List<LostPetFeedInfoResponse>> getLostPetFeedInfoList(
            @RequestParam Integer size,
            @RequestParam(required = false) FeedsSortOption sort
    ) {
        if (sort == null) {sort = FeedsSortOption.LATEST;}
        return ResponseEntity.ok().body(lostPetFeedService.getLostPetFeedInfoList(size, sort));
    }

    @GetMapping(params = {"last-feed", "size"})
    public ResponseEntity<List<LostPetFeedInfoResponse>> getLostPetFeedInfoList(
            @RequestParam("last-feed") Long lastFeedId,
            @RequestParam Integer size,
            @RequestParam(required = false) FeedsSortOption sort
    ) {
        if (sort == null) {sort = FeedsSortOption.LATEST;}
        return ResponseEntity.ok().body(lostPetFeedService.getLostPetFeedInfoList(lastFeedId, size, sort));
    }

    @GetMapping("/{feedId}")
    public ResponseEntity<LostPetFeedFullResponse> getLostPetFeedFull(@PathVariable Long feedId) {
        lostPetFeedService.increaseViewCount(feedId);
        return ResponseEntity.ok().body(lostPetFeedService.getLostPetFeedFull(feedId));
    }

    @PostMapping("/submit")
    public ResponseEntity<Void> submitFeed(@LoginMemberId Long memberId, @ModelAttribute LostPetFeedSubmitRequest dto) throws IOException {
        lostPetFeedService.submitFeed(memberId, dto);
        return ResponseEntity.ok().build();
    }
}
