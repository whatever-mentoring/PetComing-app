package community.whatever.petcoming.feed.service;

import community.whatever.petcoming.feed.domain.LostPetFeedFinder;
import community.whatever.petcoming.feed.domain.LostPetFeedInfoDto;
import community.whatever.petcoming.feed.dto.LostPetFeedInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LostPetFeedService {

    private final LostPetFeedFinder lostPetFeedFinder;

    @Transactional(readOnly = true)
    public List<LostPetFeedInfoResponse> getLostPetFeedInfoList() {
        List<LostPetFeedInfoDto> dtoList = lostPetFeedFinder.getLostPetFeedInfoList();

        return dtoList.stream()
                .map(LostPetFeedInfoResponse::of)
                .collect(Collectors.toList());
    }
}
