package community.whatever.petcoming.feed.domain;

import community.whatever.petcoming.member.domain.Member;
import community.whatever.petcoming.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class LostPetFeedFinder {

    // todo MemberFinder로 변경
    private final MemberRepository memberRepository;
    private final LostPetFeedRepository lostPetFeedRepository;

    // todo JOIN으로 변경?
    public List<LostPetFeedInfoDto> getLostPetFeedInfoList() {
        List<LostPetFeed> lostPetFeeds = lostPetFeedRepository.findAll();

        List<LostPetFeedInfoDto> dtoList = new ArrayList<>();
        for (LostPetFeed feed : lostPetFeeds) {
            Member author = memberRepository.findById(feed.getAuthorId()).orElseThrow();
            String authorNickname = author.getNickname();

            LostPetFeedInfoDto dto = LostPetFeedInfoDto.builder()
                    .feedId(feed.getId())
                    .specialNote(feed.getTitle())
                    .authorName(authorNickname)
                    .animalType(feed.getAnimalType())
                    .animalGender(feed.getAnimalGender())
                    .breed(feed.getBreed())
                    .imageUrl(null) // todo 이거 왜 없니 (3개니까 Table 하나 더 만들어야겠다)
                    .viewCount(null)
                    .likeCount(null)
                    .build();

            dtoList.add(dto);
        }

        return dtoList;
    }
}
