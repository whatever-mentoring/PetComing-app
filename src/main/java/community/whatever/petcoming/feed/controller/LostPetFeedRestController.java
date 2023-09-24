package community.whatever.petcoming.feed.controller;

import community.whatever.petcoming.config.resolver.LoginMemberId;
import community.whatever.petcoming.feed.domain.FeedsSortOption;
import community.whatever.petcoming.feed.dto.LostPetFeedFullResponse;
import community.whatever.petcoming.feed.dto.LostPetFeedGetRequest;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/feed/lost-pet")
public class LostPetFeedRestController {

    private final LostPetFeedService lostPetFeedService;

    @GetMapping(params = {"size"})
    public ResponseEntity<List<LostPetFeedInfoResponse>> getLostPetFeedInfoList(
            @LoginMemberId Long memberId,
            @ModelAttribute LostPetFeedGetRequest dto
    ) {
        FeedsSortOption sort = dto.getSort();
        if (sort == null) {
            sort = FeedsSortOption.LATEST;
        }
        return ResponseEntity.ok().body(
                lostPetFeedService.getLostPetFeedInfoList(memberId, dto.getSize(), sort,
                        dto.getKeyword(), dto.getAnimalGender(), dto.getAnimalType())
        );
    }

    @GetMapping(params = {"last-feed", "size"})
    public ResponseEntity<List<LostPetFeedInfoResponse>> getLostPetFeedInfoListWithLastFeed(
            @LoginMemberId Long memberId,
            @ModelAttribute LostPetFeedGetRequest dto
    ) {
        FeedsSortOption sort = dto.getSort();
        if (sort == null) {sort = FeedsSortOption.LATEST;}
        return ResponseEntity.ok().body(lostPetFeedService.getLostPetFeedInfoList(memberId, dto.getLastFeedId(), dto.getSize(), sort,
                dto.getKeyword(), dto.getAnimalGender(), dto.getAnimalType())
        );
    }

    @GetMapping("/{feedId}")
    public ResponseEntity<LostPetFeedFullResponse> getLostPetFeedFull(
            @LoginMemberId Long memberId,
            @PathVariable Long feedId) {
        lostPetFeedService.increaseViewCount(feedId);
        return ResponseEntity.ok().body(lostPetFeedService.getLostPetFeedFull(memberId, feedId));
    }

    @PostMapping("/submit")
    public ResponseEntity<Void> submitFeed(
            @LoginMemberId Long memberId,
            @RequestPart(value = "dto") LostPetFeedSubmitRequest dto,
            @RequestPart(value = "images") List<MultipartFile> imageFiles) throws IOException {
        lostPetFeedService.submitFeed(memberId, dto, imageFiles);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{feedId}/delete")
    public ResponseEntity<Void> deleteFeed(@LoginMemberId Long memberId, @PathVariable Long feedId) {
        lostPetFeedService.deleteFeed(memberId, feedId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{feedId}/like")
    public ResponseEntity<Long> likeFeed(
            @LoginMemberId Long memberId,
            @PathVariable Long feedId) {
        return ResponseEntity.ok().body(lostPetFeedService.likeFeed(memberId, feedId));
    }

    @PostMapping("{feedId}/unlike")
    public ResponseEntity<Long> unlikeFeed(
            @LoginMemberId Long memberId,
            @PathVariable Long feedId) {
        return ResponseEntity.ok().body(lostPetFeedService.unlikeFeed(memberId, feedId));
    }
}
