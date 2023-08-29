package community.whatever.petcoming.feed.controller;

import community.whatever.petcoming.feed.dto.LostPetFeedInfoResponse;
import community.whatever.petcoming.feed.service.LostPetFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/feed/lost-pet")
public class LostPetFeedRestController {

    private final LostPetFeedService lostPetFeedService;

    @GetMapping("/")
    public ResponseEntity<List<LostPetFeedInfoResponse>> getLostPetFeedInfoList() {
        return ResponseEntity.ok().body(lostPetFeedService.getLostPetFeedInfoList());
    }
}
