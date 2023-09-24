package community.whatever.petcoming.feed.domain;

import community.whatever.petcoming.member.domain.Member;
import community.whatever.petcoming.member.domain.MemberFinder;
import community.whatever.petcoming.uploadimage.domain.UploadImage;
import community.whatever.petcoming.uploadimage.domain.UploadImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class LostPetFeedFinder {

    private final LostPetFeedRepository lostPetFeedRepository;
    private final UploadImageRepository uploadImageRepository;
    private final FeedLikeFinder feedLikeFinder;
    private final MemberFinder memberFinder;

    public LostPetFeed findById(Long feedId) {
        return lostPetFeedRepository.findById(feedId).orElseThrow();
    }

    public void existsOrThrowById(Long feedId) {
        if (!lostPetFeedRepository.existsById(feedId)) {
            throw new EntityNotFoundException("LostPetFeedId=" + feedId + " not found");
        }
    }

    public List<LostPetFeedInfoDto> getLostPetFeedInfoList(Long loginMemberId, Long lastFeedId, Integer size, FeedsSortOption sort, String keyword, AnimalGender animalGender, AnimalType animalType) {
        Pageable pageable = sort.getPageable(size);

        List<LostPetFeed> lostPetFeeds = lostPetFeedRepository.findWithFilters(lastFeedId, keyword, animalGender, animalType, pageable);

        List<LostPetFeedInfoDto> dtoList = new ArrayList<>();
        for (LostPetFeed feed : lostPetFeeds) {
            Member author = memberFinder.findById(feed.getAuthorId());
            String authorNickname = author.getNickname();

            Long countFeedLiker = feedLikeFinder.countFeedLikeByFeedCategoryAndFeedId("lost", feed.getId());
            boolean loginMemberLiked = feedLikeFinder.existsByFeedCategoryAndFeedIdAndLikerId("lost", feed.getId(), loginMemberId);

            UploadImage image = uploadImageRepository.findFirstByFeedCategoryAndFeedIdOrderByIdAsc("lost", feed.getId()).orElseThrow();

            LostPetFeedInfoDto dto = LostPetFeedInfoDto.builder()
                    .feedId(feed.getId())
                    .specialNote(feed.getTitle())
                    .authorName(authorNickname)
                    .animalType(feed.getAnimalType())
                    .animalGender(feed.getAnimalGender())
                    .breed(feed.getBreed())
                    .lostArea(feed.getLostArea())
                    .imageUrl(image.getUploadUrl())
                    .viewCount(feed.getViewCount())
                    .likeCount(countFeedLiker)
                    .liked(loginMemberLiked)
                    .build();
            dtoList.add(dto);
        }

        return dtoList;
    }

    public LostPetFeedFullDto getLostPetFeedFull(Long loginMemberId, Long feedId) {
        LostPetFeed feed = findById(feedId);
        String details = feed.getContent();

        Member author = memberFinder.findById(feed.getAuthorId());
        String authorNickname = author.getNickname();

        Long countFeedLiker = feedLikeFinder.countFeedLikeByFeedCategoryAndFeedId("lost", feed.getId());
        boolean loginMemberLiked = feedLikeFinder.existsByFeedCategoryAndFeedIdAndLikerId("lost", feed.getId(), loginMemberId);

        List<UploadImage> images = uploadImageRepository.findByFeedCategoryAndFeedIdOrderByFeedIdAsc("lost", feedId);
        List<String> imageUrls = images.stream()
                .map(UploadImage::getUploadUrl)
                .collect(Collectors.toList());

        return LostPetFeedFullDto.builder()
                .feedId(feedId)
                .specialNote(feed.getTitle())
                .authorName(authorNickname)
                .animalType(feed.getAnimalType())
                .animalGender(feed.getAnimalGender())
                .breed(feed.getBreed())
                .lostArea(feed.getLostArea())
                .contact(feed.getContact())
                .viewCount(feed.getViewCount())
                .likeCount(countFeedLiker)
                .liked(loginMemberLiked)
                .imageUrls(imageUrls)
                .details(details)
                .createDate(feed.getCreateDate())
                .build();
    }

    public LostPetFeed isAuthorOrThrow(Long accessorId, Long feedId) {
        LostPetFeed feed = findById(feedId);
        Long authorId = feed.getAuthorId();
        if (!accessorId.equals(authorId)) {
            throw new AccessDeniedException("작성자가 아닙니다.");
        }
        return feed;
    }
}
