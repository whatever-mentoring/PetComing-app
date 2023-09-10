package community.whatever.petcoming.feed.domain;

import community.whatever.petcoming.member.domain.Member;
import community.whatever.petcoming.member.domain.MemberFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class LostPetFeedFinder {

    private final LostPetFeedRepository lostPetFeedRepository;
    private final MemberFinder memberFinder;

    public LostPetFeed findById(Long feedId) {
        return lostPetFeedRepository.findById(feedId).orElseThrow();
    }

    public void existsOrThrowById(Long feedId) {
        if (!lostPetFeedRepository.existsById(feedId)) {
            throw new EntityNotFoundException("LostPetFeedId=" + feedId + " not found");
        }
    }

    public List<LostPetFeedInfoDto> getLostPetFeedInfoList(Long lastFeedId, Integer size, FeedsSortOption sort) {
        Pageable pageable = sort.getPageable(size);
        List<LostPetFeed> lostPetFeeds = lostPetFeedRepository.findByIdLessThan(lastFeedId, pageable);

        List<LostPetFeedInfoDto> dtoList = new ArrayList<>();
        for (LostPetFeed feed : lostPetFeeds) {
            Member author = memberFinder.findById(feed.getAuthorId());
            String authorNickname = author.getNickname();

            LostPetFeedInfoDto dto = LostPetFeedInfoDto.builder()
                    .feedId(feed.getId())
                    .specialNote(feed.getTitle())
                    .authorName(authorNickname)
                    .animalType(feed.getAnimalType())
                    .animalGender(feed.getAnimalGender())
                    .breed(feed.getBreed())
                    .lostArea(feed.getLostArea())
                    // todo 이거 왜 없니 (3개니까 Table 하나 더 만들어야겠다)
                    .imageUrl("https://i.namu.wiki/i/BMOGQ_hFSF4xHK_oOo127aa5LHsxE28Kkomve6Yt4hfKQkAPWaqEIqsaCN2rVq2QnsLz3QFihlMF9ACZfjeK0XeB7j2GUEkIz1kJkm6c_pMwN4wwGSBBugiJ0QYQm7A2IDPXlw_9y9GzOxPJHsSx4g.webp")
                    .viewCount(feed.getViewCount())
                    .likeCount(100L)
                    .build();
            dtoList.add(dto);
        }

        return dtoList;
    }

    public LostPetFeedFullDto getLostPetFeedFull(Long feedId) {
        LostPetFeed feed = findById(feedId);
        String details = feed.getContent();
        Member author = memberFinder.findById(feed.getAuthorId());
        String authorNickname = author.getNickname();

        return LostPetFeedFullDto.builder()
                .feedId(feedId)
                .specialNote(feed.getTitle())
                .authorName(authorNickname)
                .animalType(feed.getAnimalType())
                .animalGender(feed.getAnimalGender())
                .breed(feed.getBreed())
                .lostArea(feed.getLostArea())
                .viewCount(feed.getViewCount())
                .likeCount(100L)
                .imageUrl("https://i.namu.wiki/i/BMOGQ_hFSF4xHK_oOo127aa5LHsxE28Kkomve6Yt4hfKQkAPWaqEIqsaCN2rVq2QnsLz3QFihlMF9ACZfjeK0XeB7j2GUEkIz1kJkm6c_pMwN4wwGSBBugiJ0QYQm7A2IDPXlw_9y9GzOxPJHsSx4g.webp")
                .details(details)
                .createDate(feed.getCreateDate())
                .build();
    }
}
