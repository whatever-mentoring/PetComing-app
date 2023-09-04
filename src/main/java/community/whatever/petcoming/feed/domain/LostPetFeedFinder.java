package community.whatever.petcoming.feed.domain;

import community.whatever.petcoming.member.domain.Member;
import community.whatever.petcoming.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class LostPetFeedFinder {

    // todo MemberFinder로 변경
    private final MemberRepository memberRepository;
    private final LostPetFeedRepository lostPetFeedRepository;

    public LostPetFeed findById(Long feedId) {
        return lostPetFeedRepository.findById(feedId).orElseThrow();
    }

    public List<LostPetFeedInfoDto> getLostPetFeedInfoList(Long lastFeedId, Integer size, FeedsSortOption sort) {
        Pageable pageable = sort.getPageable(size);
        List<LostPetFeed> lostPetFeeds = lostPetFeedRepository.findByIdLessThan(lastFeedId, pageable);

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
                    // todo 이거 왜 없니 (3개니까 Table 하나 더 만들어야겠다)
                    .imageUrl("https://i.namu.wiki/i/BMOGQ_hFSF4xHK_oOo127aa5LHsxE28Kkomve6Yt4hfKQkAPWaqEIqsaCN2rVq2QnsLz3QFihlMF9ACZfjeK0XeB7j2GUEkIz1kJkm6c_pMwN4wwGSBBugiJ0QYQm7A2IDPXlw_9y9GzOxPJHsSx4g.webp")
                    .viewCount(100L)
                    .likeCount(100L)
                    .build();
            dtoList.add(dto);
        }

        return dtoList;
    }

    public LostPetFeedFullDto getLostPetFeedFull(Long feedId) {
        LostPetFeed feed = findById(feedId);
        String details = feed.getContent();
        Member author = memberRepository.findById(feed.getAuthorId()).orElseThrow();
        String authorNickname = author.getNickname();

        return LostPetFeedFullDto.builder()
                .feedId(feedId)
                .specialNote(feed.getTitle())
                .authorName(authorNickname)
                .animalType(feed.getAnimalType())
                .animalGender(feed.getAnimalGender())
                .breed(feed.getBreed())
                .viewCount(100L)
                .likeCount(100L)
                .imageUrl("https://i.namu.wiki/i/BMOGQ_hFSF4xHK_oOo127aa5LHsxE28Kkomve6Yt4hfKQkAPWaqEIqsaCN2rVq2QnsLz3QFihlMF9ACZfjeK0XeB7j2GUEkIz1kJkm6c_pMwN4wwGSBBugiJ0QYQm7A2IDPXlw_9y9GzOxPJHsSx4g.webp")
                .details(details)
                .createDate(feed.getCreateDate())
                .build();
    }
}
