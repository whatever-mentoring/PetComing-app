package community.whatever.petcoming.feed.service;

import community.whatever.petcoming.common.infra.S3Uploader;
import community.whatever.petcoming.feed.domain.AnimalGender;
import community.whatever.petcoming.feed.domain.AnimalType;
import community.whatever.petcoming.feed.domain.FeedContent;
import community.whatever.petcoming.feed.domain.FeedsSortOption;
import community.whatever.petcoming.feed.domain.LostPetFeed;
import community.whatever.petcoming.feed.domain.LostPetFeedEditor;
import community.whatever.petcoming.feed.domain.LostPetFeedFinder;
import community.whatever.petcoming.feed.domain.LostPetFeedFullDto;
import community.whatever.petcoming.feed.domain.LostPetFeedInfoDto;
import community.whatever.petcoming.feed.domain.LostPetFeedRepository;
import community.whatever.petcoming.feed.dto.LostPetFeedFullResponse;
import community.whatever.petcoming.feed.dto.LostPetFeedInfoResponse;
import community.whatever.petcoming.feed.dto.LostPetFeedSubmitRequest;
import community.whatever.petcoming.uploadimage.domain.UploadImage;
import community.whatever.petcoming.uploadimage.domain.UploadImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LostPetFeedService {

    private final int MAX_FEED_IMAGE_COUNT = 3;

    private final LostPetFeedRepository lostPetFeedRepository;
    private final UploadImageRepository uploadImageRepository;

    private final S3Uploader s3Uploader;

    private final LostPetFeedFinder lostPetFeedFinder;
    private final LostPetFeedEditor lostPetFeedEditor;

    @Transactional(readOnly = true)
    public List<LostPetFeedInfoResponse> getLostPetFeedInfoList(Long memberId, Integer size, FeedsSortOption sort, String keyword, AnimalGender animalGender, AnimalType animalType) {
        // 클래스 내부 코드 호출
        return getLostPetFeedInfoList(memberId, Long.MAX_VALUE, size, sort, keyword, animalGender, animalType);
    }

    @Transactional(readOnly = true)
    public List<LostPetFeedInfoResponse> getLostPetFeedInfoList(Long memberId, Long lastFeedId, Integer size, FeedsSortOption sort, String keyword, AnimalGender animalGender, AnimalType animalType) {
        List<LostPetFeedInfoDto> dtoList = lostPetFeedFinder.getLostPetFeedInfoList(memberId, lastFeedId, size, sort, keyword, animalGender, animalType);

        return dtoList.stream()
                .map(LostPetFeedInfoResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LostPetFeedFullResponse getLostPetFeedFull(Long loginMemberId, Long feedId) {
        LostPetFeedFullDto fullDto = lostPetFeedFinder.getLostPetFeedFull(loginMemberId, feedId);
        return LostPetFeedFullResponse.of(fullDto);
    }

    @Transactional
    public void increaseViewCount(Long feedId) {
        lostPetFeedEditor.increaseViewCount(feedId);
    }

    @Transactional
    public void submitFeed(Long memberId, LostPetFeedSubmitRequest dto, List<MultipartFile> imageFiles) throws IOException {
        FeedContent feedContent = new FeedContent(null, dto.getDetails()); // 알아서 저장됨

        LostPetFeed feed = LostPetFeed.builder()
                .title(dto.getSpecialNote())
                .content(feedContent)
                .authorId(memberId)
                .animalType(dto.getAnimalType())
                .animalGender(dto.getAnimalGender())
                .breed(dto.getBreed())
                .lostArea(dto.getLostArea())
                .contact(dto.getContact())
                .build();

        feed = lostPetFeedRepository.save(feed);

        int count = 0;
        for (MultipartFile file : imageFiles) {
            count++;
            if (count > MAX_FEED_IMAGE_COUNT) break;

            String path = s3Uploader.upload(file, "image");

            UploadImage uploadImage = UploadImage.builder()
                    .feedCategory("lost")
                    .feedId(feed.getId())
                    .uploadUrl(path)
                    .build();

            uploadImageRepository.save(uploadImage);
        }
    }

    @Transactional
    public void deleteFeed(Long memberId, Long feedId) {
        LostPetFeed myFeed = lostPetFeedFinder.isAuthorOrThrow(memberId, feedId);
        myFeed.softDelete();
    }

    @Transactional
    public Long likeFeed(Long memberId, Long feedId) {
        return lostPetFeedEditor.likeFeed(memberId, feedId);
    }

    @Transactional
    public Long unlikeFeed(Long memberId, Long feedId) {
        return lostPetFeedEditor.unlikeFeed(memberId, feedId);
    }
}
